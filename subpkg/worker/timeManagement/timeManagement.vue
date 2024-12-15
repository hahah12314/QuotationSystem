<template>

	<view class="container">

		<view class="uni-container">
			<!-- 工时管理筛选部分 -->
			<view class="filter-bar">
				<input type="text" class="filter-input" placeholder="请输入工人姓名" v-model="filters.workerName" />
				<uni-data-select v-model="filters.project" :localdata="projectOptions" @change="onProjectChange"
					:clear="false" class="filter-select"></uni-data-select>
				<uni-data-select v-model="filters.sortBy" :localdata="sortOptions" @change="onSortChange" :clear="false"
					class="filter-select"></uni-data-select>


				<button class="filter-button" @click="applyFilters">查询</button>
			</view>


			<!-- 工时卡片展示 -->
			<view class="card-container">
				<view class="worker-card" v-for="(workHour, index) in nowDataList" :key="workHour.id">
					<view class="card-header">
						<text class="worker-name">{{ workHour.processName }}</text>
						<text class="worker-index">#{{ (pageIndex - 1) * pageSize + index + 1 }}</text>
					</view>
					<view class="card-body">
						<text>工序名称: {{ workHour.processName }}</text>
						<text>工人名字: {{ workHour.workerName }}</text>
						<text>成本: {{ workHour.cost }} 元</text>
						<text>工时: {{ workHour.hour }} 小时</text>
					</view>
					<!--          <view class="card-footer">

            <button class="custom-button delete" @click="handleDelete(workHour)">删除</button>
          </view> -->
				</view>
			</view>

			<!-- 分页 -->
			<uni-section title="分页" type="line" padding>
				<div class="pagination">
					<button :disabled="pageIndex === 1" @click="prevPage">上一页</button>
					<span>{{ pageIndex }} / {{ totalPage }} ({{ totalItem }} 条)</span>
					<button :disabled="pageIndex === totalPage" @click="nextPage">下一页</button>
				</div>
			</uni-section>
		</view>

		<!-- 自定义删除确认弹窗 -->
		<view class="popup-overlay" v-if="isDeletePopupVisible" @click="closeDeletePopup">
			<view class="popup-content" @click.stop>
				<view class="popup-header">
					<text class="popup-title">确认删除</text>
				</view>
				<view class="popup-body">
					<text>确定要删除该工时吗？</text>
				</view>
				<view class="popup-footer">
					<button class="popup-button" @click="confirmDelete">确认</button>
					<button class="popup-button" @click="closeDeletePopup">取消</button>
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				originalDataList: [],
				filteredDataList: [],
				nowDataList: [],
				pageSize: 5,
				pageIndex: 1,
				totalPage: 0,
				totalItem: 0, // 总条数
				filters: {
					workerName: '',
					project: 0,
					sortBy: 0
				},
				projectOptions: [{
						value: 0,
						text: '全部'
					},
					{
						value: 1,
						text: '锯'
					},
					{
						value: 2,
						text: '镗铣'
					},
					{
						value: 3,
						text: '弯'
					},
					{
						value: 4,
						text: '钻'
					},
					{
						value: 5,
						text: '车'
					},
					{
						value: 6,
						text: '外磨'
					},
					{
						value: 7,
						text: '铣'
					},
					{
						value: 8,
						text: '校平'
					},
					{
						value: 9,
						text: '焊'
					},
					{
						value: 10,
						text: '打磨'
					},
					{
						value: 11,
						text: '装'
					}
				],
				sortOptions: [{
						value: 0,
						text: '默认'
					},
					{
						value: 1,
						text: '按工时升序'
					},
					{
						value: 2,
						text: '按工时降序'
					},
					{
						value: 3,
						text: '按成本升序'
					},
					{
						value: 4,
						text: '按成本降序'
					}
				],
				isDeletePopupVisible: false,
				deleteWorkhour: null
			};
		},
		methods: {
			async fetchAllData() {
				// 您的获取数据逻辑保持不变
				this.originalDataList = [];
				this.pageIndex = 1;
				let currentPage = 1;
				let totalPages = 1;
				try {
					while (currentPage <= totalPages) {
						const res = await uni.$http.get('/workhours/list', {
							pageIndex: currentPage,
							pageSize: 1000
						});
						if (res.statusCode === 200 && res.data.data) {
							const data = res.data.data;
							this.originalDataList = this.originalDataList.concat(data.list);
							totalPages = data.totalPage;
							currentPage++;
						} else {
							throw new Error('数据请求失败');
						}
					}
					this.applyFilters();
				} catch (error) {
					console.error('获取所有数据时出错:', error);
					uni.showToast({
						title: '获取数据列表时发生错误',
						icon: 'error'
					});
				}
			},
			applyFilters() {
				this.pageIndex = 1;
				this.filteredDataList = this.originalDataList.filter(worker => {
					const nameMatch = worker.workerName.includes(this.filters.workerName.trim());
					console.log(nameMatch, this.filters)
					const projectMatch = this.filters.project === 0 || worker.processName === this.projectOptions[
						this.filters.project].text;
					console.log('project', projectMatch, this.filters)
					return nameMatch && projectMatch;
				});

				if (this.filters.sortBy > 0) {
					const sortFunctions = {
						1: (a, b) => a.hour - b.hour,
						2: (a, b) => b.hour - a.hour,
						3: (a, b) => a.cost - b.cost,
						4: (a, b) => b.cost - a.cost
					};
					this.filteredDataList.sort(sortFunctions[this.filters.sortBy]);
				}

				this.totalItem = this.filteredDataList.length; // 更新总条数
				this.totalPage = Math.ceil(this.totalItem / this.pageSize); // 更新总页数
				this.updatePaginatedData();
			},
			updatePaginatedData() {
				const start = (this.pageIndex - 1) * this.pageSize;
				const end = this.pageIndex * this.pageSize;
				this.nowDataList = this.filteredDataList.slice(start, end);
			},
			changePage() {
				this.updatePaginatedData();
			},
			prevPage() {
				if (this.pageIndex > 1) {
					this.pageIndex--;
					this.changePage();
				}
			},
			nextPage() {
				if (this.pageIndex < this.totalPage) {
					this.pageIndex++;
					this.changePage();
				}
			},
			onProjectChange(e) {
				this.filters.project = parseInt(e);

				this.applyFilters();
			},
			onSortChange(e) {
				this.filters.sortBy = parseInt(e);
				this.applyFilters();
			},
			handleDelete(workhour) {
				this.deleteWorkhour = {
					...workhour
				};
				console.log('wh', this.deleteWorkhour, workhour)
				this.isDeletePopupVisible = true;
			},

			confirmDelete() {
				// 调用删除接口
				uni.$http.delete('/workhours/delete', {
					id: this.deleteWorkhour.id
				}).then(res => {
					if (res.statusCode === 200) {
						uni.showToast({
							title: '删除成功',
							icon: 'success'
						});
						this.closeDeletePopup();
						this.fetchAllData();
					} else {
						uni.showToast({
							title: '删除失败',
							icon: 'error'
						});
					}
				}).catch(error => {
					console.error('删除工时信息时出错:', error);
					uni.showToast({
						title: '删除失败',
						icon: 'error'
					});
				});
			},
			closeDeletePopup() {
				this.isDeletePopupVisible = false;
			},
		},
		onLoad() {
			this.fetchAllData();
		}
	};
</script>
<style lang="scss">
	.container {
		height: 100vh;
		/* 默认高度为视口高度 */
		background-color: #e7eaf4;
		overflow-y: auto;
		/* 如果内容超过视口高度，显示滚动条 */
	}

	.uni-container {
		padding: 10px;

		.pagination {
			margin-top: 10px;
			display: flex;
			align-items: center;
			justify-content: center;
		}

		.pagination button {
			margin: 0 10px;
		}
	}

	.filter-bar {
		display: flex;
		align-items: center;
		flex-wrap: wrap;
		gap: 10px;
		margin-bottom: 15px;

	}

	.filter-input {
		flex: 1 1 200px;
		padding: 8px 12px;
		font-size: 14px;
		border: 1px solid #fff;
		border-radius: 20px;
		height: 20px;
		background-color: #fff;
		color: #333;
		transition: border-color 0.3s, box-shadow 0.3s;
	}


	.filter-select {
		flex: 1 1 150px;
		border: 1px solid #fff;
		height: 35px;
		border-radius: 35px;
		background-color: #fff;
		color: #666;
		transition: border-color 0.3s, box-shadow 0.3s;
	}

	.filter-select-view {
		padding: 8px 12px;
		font-size: 14px;
		color: #666;
		width: 100%;
		text-align: left;
	}

	.filter-button {

		font-size: 14px;
		color: #fff;
		background-color: #409eff;
		border: none;
		border-radius: 5px;
		cursor: pointer;
		transition: background-color 0.3s;
	}

	.filter-button:hover {
		background-color: #3a8ee6;
	}

	.card-container {
		display: flex;
		flex-wrap: wrap;
		gap: 15px;
	}

	.worker-card {
		width: 100%;
		padding: 15px;
		background-color: #fff;
		border: 1px solid #e0e0e0;
		border-radius: 10px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		display: flex;
		flex-direction: column;
		justify-content: space-between;

		.card-header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 10px;

			.worker-name {
				font-size: 16px;
				font-weight: bold;
			}

			.worker-index {
				font-size: 12px;
				color: #888;
			}
		}

		.card-body {
			display: flex;
			flex-direction: column;
			gap: 5px;

			text {
				font-size: 14px;
				color: #555;
			}
		}

		.card-footer {
			display: flex;
			justify-content: space-between;
			margin-top: 10px;

			.custom-button {

				font-size: 14px;
				border: none;
				border-radius: 5px;
				color: #fff;
				cursor: pointer;
			}

			.edit {
				background-color: #409eff;
			}

			.delete {
				background-color: #ff4d4f;
			}
		}

	}

	/* 弹窗背景层 */
	.popup-overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.5);
		display: flex;
		justify-content: center;
		align-items: center;
		z-index: 1000;

		/* 弹窗内容容器 */
		.popup-content {
			background-color: #fff;
			border-radius: 8px;
			box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
			width: 90%;

			overflow: hidden;
		}

		/* 弹窗头部 */
		.popup-header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 16px;
			border-bottom: 1px solid #e0e0e0;
		}

		/* 弹窗标题 */
		.popup-title {
			font-size: 18px;
			font-weight: bold;
			color: #333;
		}

		/* 关闭按钮 */
		.popup-close {
			background: none;
			border: none;
			font-size: 18px;
			color: #999;
			cursor: pointer;
		}

		/* 弹窗主体 */
		.popup-body {
			padding: 16px;
		}

		/* 输入框 */
		.popup-body input {
			flex: 1 1 200px;
			padding: 8px 12px;
			margin: 5px 0px;
			font-size: 14px;
			border: 1px solid #888;
			border-radius: 20px;
			height: 20px;
			background-color: #fff;
			color: #333;
			transition: border-color 0.3s, box-shadow 0.3s;
		}

		.popup-select {
			display: flex;
			border-radius: 20px;
			padding: 8px 12px;
			height: 20px;
			border: 1px solid #888;
			margin: 5px 0px;
			align-items: center;

			text {
				font-size: 14px;
				color: #555;
			}
		}

		/* 弹窗底部 */
		.popup-footer {
			display: flex;
			justify-content: center;
			align-items: center;

			border-top: 1px solid #e0e0e0;
		}

		/* 按钮 */
		.popup-button {
			background-color: #007bff;
			color: #fff;
			border: none;
			height: 40px;
			width: 80px;
			display: flex;
			align-items: center;
			justify-content: center;
			margin: 10px;

			border-radius: 4px;
			cursor: pointer;
			transition: background-color 0.3s;
		}

		.popup-button:hover {
			background-color: #0056b3;
		}
	}
</style>