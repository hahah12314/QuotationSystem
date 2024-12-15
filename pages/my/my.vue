<template>
	<view class="container">
		<view class="profile-section">
			<image class="avatar" :src="user.avatar" mode="aspectFill"></image>
			<view class="user-info">
				<text class="username">{{ user.username }}</text>
				<text class="role">{{ user.name }}</text>
        <text class="role">{{ user.roleName }}</text>
			</view>
		</view>

		<view class="function-section">
			<view class="function-item" @click="goToChangePassword">
				<uni-icons type="locked" size="24"></uni-icons>
				<text class="function-text">修改密码</text>
				<text class="icon-right-arrow">></text>
			</view>
			<view class="function-item" @click="goToEditProfile">
				<uni-icons type="gear" size="24"></uni-icons>
				<text class="function-text">个人信息</text>
				<text class="icon-right-arrow">></text>
			</view>
			<view class="function-item" @click="logout">
				<uni-icons type="undo" size="24"></uni-icons>
				<text class="function-text">退出登录</text>
				<text class="icon-right-arrow">></text>
			</view>
		</view>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				user: {
					avatar: '/static/avatar.jpeg', // 替换为实际头像URL
					username: '张三',
					role: '普通用户'
				}
			};
		},
		onLoad() {
			// 数据回显
			const userInfo = uni.getStorageSync("userInfo");
			this.user=userInfo
      this.user.avatar=userInfo.avatar || require('@/static/user1.png');
			// this.user.role = userInfo.role;
		},
		methods: {
			goToChangePassword() {
				uni.navigateTo({
					url: '/subpkg/change/changPassword/changPassword'
				});
			},
			goToEditProfile() {
				uni.navigateTo({
					url: '/subpkg/change/changeInfo/changeInfo'
				});
			},
			logout() {
				uni.showModal({
					title: '提示',
					content: '确定要退出登录吗？',
					success: (res) => {
						if (res.confirm) {
							// 清除用户信息和token
							uni.removeStorageSync('user');
							uni.removeStorageSync('token');
							uni.reLaunch({
								url: '/subpkg/login/login'
							});
						}
					}
				});
			}
		}
	};
</script>
<style scoped>
	.container {
		display: flex;
		flex-direction: column;
		align-items: center;

		background-color: #e7eaf4;
		height: 100vh;
		box-sizing: border-box;
	}

	.profile-section {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-bottom: 20px;
	}

	.avatar {
		width: 100px;
		height: 100px;
		border-radius: 50px;
		margin-bottom: 10px;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	}

	.user-info {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		text-align: center;
	}

	.username {
		font-size: 22px;
		font-weight: bold;
		margin-bottom: 5px;
		color: #333;
	}

	.role {
		font-size: 12px;
		color: #666;
	}

	.function-section {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 100%;
	}

	.function-item {
		display: flex;
		align-items: center;
		justify-content: space-between;
		width: 80%;
		padding: 15px;
		background-color: #fff;
		border-radius: 5px;
		margin: 10px 0;
		border-radius: 10px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}

	.iconfont {
		font-family: "iconfont" !important;
		font-size: 20px;
		color: #1aad19;
		margin-right: 10px;
	}

	.function-text {
		flex: 1;
		font-size: 16px;
		color: #333;
		margin-left: 12px;
	}

	.icon-right-arrow {
		font-size: 16px;
		color: #999;
	}
</style>