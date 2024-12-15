<template>
	<view class="container">
		<view class="uni-container">
			<!-- 工人管理筛选部分 -->
			<view class="filter-bar">
				<input type="text" class="filter-input" placeholder="请输入工人姓名" v-model="filters.workerName" />
				<uni-data-select v-model="filters.gender" :localdata="genderOptions" @change="onGenderChange"
					:clear="false" class="filter-select"></uni-data-select>
				<uni-data-select v-model="filters.level" :localdata="levelOptions" @change="onLevelChange"
					:clear="false" class="filter-select"></uni-data-select>
				<uni-data-select v-model="filters.sortBy" :localdata="sortOptions" @change="onSortChange" :clear="false"
					class="filter-select"></uni-data-select>
				<button class="filter-button" @click="applyFilters">查询</button>
			</view>
			<!-- 工人卡片展示 -->
			<view class="card-container">
				<view class="worker-card" v-for="(worker, index) in nowDataList" :key="worker.id">
					<view class="card-header">
						<text class="worker-name">{{ worker.workerName }}</text>
						<text class="worker-index">#{{ (pageIndex - 1) * pageSize + index + 1 }}</text>
					</view>
					<view class="card-body">
						<text>年龄: {{ worker.age }}</text>
						<text>性别: {{ worker.gender }}</text>
						<text>级别: {{ worker.level }}</text>
						<text>入职日期: {{ worker.hireDate }}</text>
					</view>
					<!--          <view class="card-footer">
            <button class="custom-button edit" @click="handleEdit(worker)">编辑</button>
            <button class="custom-button delete" @click="handleDelete(worker)">删除</button>
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
		<view class="popup-overlay" v-if="isEditPopupVisible" @click="closeEditPopup">
			<view class="popup-content" @click.stop>
				<view class="popup-header">
					<text class="popup-title">编辑工人信息</text>
				</view>
				<view class="popup-body">
					<input type="text" v-model="editWorker.workerName" placeholder="姓名" />
					<input type="number" v-model="editWorker.age" placeholder="年龄" />
					<view class="popup-select">
						<text>性别</text>
						<uni-data-select v-model="editWorker.gender" :localdata="filteredGenderOptions"
							@change="onGenderChange" :clear="false"></uni-data-select>
					</view>
					<view class="popup-select">
						<text>级别</text>
						<uni-data-select v-model="editWorker.level" :localdata="filteredLevelOptions"
							@change="onLevelChange" :clear="false"></uni-data-select>
					</view>
					<input type="date" v-model="editWorker.hireDate" placeholder="入职日期" />
				</view>
				<view class="popup-footer">
					<button class="popup-button" @click="confirmEdit">确认</button>
					<button class="popup-button" @click="closeEditPopup">取消</button>
				</view>
			</view>
		</view>
		<!-- 自定义删除确认弹窗 -->
		<view class="popup-overlay" v-if="isDeletePopupVisible" @click="closeDeletePopup">
			<view class="popup-content" @click.stop>
				<view class="popup-header">
					<text class="popup-title">确认删除</text>
				</view>
				<view class="popup-body">
					<text>确定要删除该工人吗？</text>
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
				totalItem: 0,
				filters: {
					workerName: '',
					gender: 0,
					level: 0,
					sortBy: 0,
				},
				genderOptions: [{
						value: 0,
						text: '全部'
					},
					{
						value: 1,
						text: '男'
					},
					{
						value: 2,
						text: '女'
					}
				],
				levelOptions: [{
						value: 0,
						text: '全部'
					},
					{
						value: 1,
						text: '初级'
					},
					{
						value: 2,
						text: '中级'
					},
					{
						value: 3,
						text: '高级'
					}
				],
				sortOptions: [{
						value: 0,
						text: '默认'
					},
					{
						value: 1,
						text: '年龄升序'
					},
					{
						value: 2,
						text: '年龄降序'
					},
					{
						value: 3,
						text: '入职日期升序'
					},
					{
						value: 4,
						text: '入职日期降序'
					}
				],
				isEditPopupVisible: false,
				isDeletePopupVisible: false,
				editWorker: {
					workerId: '',
					workerName: '',
					age: '',
					gender: '',
					level: '',
					hireDate: ''
				},
				deleteWorker: null
			};
		},
		computed: {
			filteredGenderOptions() {
				return this.genderOptions.slice(1);
			},
			filteredLevelOptions() {
				return this.levelOptions.slice(1);
			}
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
						const res = await uni.$http.get('/workers/list', {
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
					const genderMatch = this.filters.gender === 0 || worker.gender === this.genderOptions[this
							.filters.gender]
						.text;
					const levelMatch = this.filters.level === 0 || worker.level === this.levelOptions[this.filters
							.level]
						.text;
					return nameMatch && genderMatch && levelMatch;
				});
				if (this.filters.sortBy > 0) {
					const sortFunctions = {
						1: (a, b) => a.age - b.age,
						2: (a, b) => b.age - a.age,
						3: (a, b) => new Date(a.hireDate) - new Date(b.hireDate),
						4: (a, b) => new Date(b.hireDate) - new Date(a.hireDate),
					};
					this.filteredDataList.sort(sortFunctions[this.filters.sortBy]);
				}
				this.totalItem = this.filteredDataList.length; // 更新总条数
				this.totalPage = Math.ceil(this.totalItem / this.pageSize); // 更新总页数
				console.log(this.totalPage)
				this.updatePaginatedData();
			},
			updatePaginatedData() {
				const start = (this.pageIndex - 1) * this.pageSize;
				const end = this.pageIndex * this.pageSize;
				this.nowDataList = this.filteredDataList.slice(start, end);
				console.log(start, end, this.nowDataList, this.pageSize, this.pageIndex, this.totalItem, this.totalPage)
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
			onGenderChange(e) {
				console.log(e)
				this.filters.gender = e;
				this.applyFilters();
			},
			onLevelChange(e) {
				this.filters.level = e;
				this.applyFilters();
			},
			onSortChange(e) {
				this.filters.sortBy = e;
				this.applyFilters();
			},
			handleEdit(worker) {

				this.editWorker = {
					...worker,
					gender: this.getGenderValue(worker.gender),
					level: this.getLevelValue(worker.level)
				};
				console.log('worker', worker, 'www', this.editWorker)
				this.isEditPopupVisible = true;
			},
			getGenderValue(genderText) {
				const option = this.genderOptions.find(option => option.text === genderText);
				return option ? option.value : null;
			},
			getLevelValue(levelText) {
				const option = this.levelOptions.find(option => option.text === levelText);
				return option ? option.value : null;
			},
			getGenderText(genderValue) {
				const option = this.genderOptions.find(option => option.value === genderValue);
				return option ? option.text : '';
			},
			getLevelText(levelValue) {
				const option = this.levelOptions.find(option => option.value === levelValue);
				return option ? option.text : '';
			},
			handleDelete(worker) {
				this.deleteWorker = {
					...worker
				};
				this.isDeletePopupVisible = true;
			},
			onEditGenderChange(e) {
				this.editWorker.gender = e;
			},
			onEditLevelChange(e) {
				this.editWorker.level = e;
			},
			confirmEdit() {
				// 调用更新接口
				this.editWorker = {
					...this.editWorker,
					gender: this.getGenderText(this.editWorker.gender),
					level: this.getLevelText(this.editWorker.level)
				};
				console.log('update', this.editWorker)
				uni.$http.post('/workers/update', this.editWorker).then(res => {
					if (res.statusCode === 200) {
						uni.showToast({
							title: '编辑成功',
							icon: 'success'
						});
						this.closeEditPopup();
						this.fetchAllData();
					} else {
						uni.showToast({
							title: '编辑失败',
							icon: 'error'
						});
					}
				}).catch(error => {
					console.error('编辑工人信息时出错:', error);
					uni.showToast({
						title: '编辑失败',
						icon: 'error'
					});
				});
			},
			closeEditPopup() {
				this.isEditPopupVisible = false;
			},
			confirmDelete() {
				// 调用删除接口
				uni.$http.delete('/workers/delete', {
					id: this.deleteWorker.id
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
					console.error('删除工人信息时出错:', error);
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
		},
	};
</script>
<style lang="less">
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



		.uni-select {
			border: none;
		}

		/* 选择器显示文本 */
		.filter-select-view {
			display: flex;
			justify-content: space-between;
			align-items: center;
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