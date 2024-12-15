<template>
    <div class="person-info-container">
        <div class="person-info-card">
            <div class="avatar-container">
                <img :src="userInfo.avatar || require('@/assets/images/user1.png')" alt="Avatar" class="avatar"
                    @click="changeAvatar" />
                <input v-if="isEditing" type="file" ref="avatarInput" @change="onFileChange" style="display:none" />
            </div>
            <div class="info-container">
                <div class="info-item">
                    <label>用户名:</label>
                    <span class="right-align">{{ userInfo.username }}</span>

                </div>
                <div class="info-item">
                    <label>角色:</label>
                    <span class="right-align">{{ userInfo.roleName }}</span>
                </div>
                <div class="info-item">
                    <label>姓名:</label>
                    <input v-if="isEditing" v-model="userInfo.name" placeholder="请输入姓名" class="custom-input" />
                    <span v-else class="right-align">{{ userInfo.name }}</span>

                </div>
                <div class="info-item">
                    <label>邮箱:</label>
                    <input v-if="isEditing" v-model="userInfo.email" placeholder="请输入邮箱" class="custom-input" />
                    <span v-else class="right-align">{{ userInfo.email }}</span>

                </div>
                <div class="info-item">
                    <label>手机号:</label>
                    <input v-if="isEditing" v-model="userInfo.mobile" placeholder="请输入手机号" class="custom-input" />
                    <span v-else class="right-align">{{ userInfo.mobile }}</span>

                </div>

                <div class="button-group">
                    <el-button v-if="!isEditing" type="primary" @click="toggleEdit">修改</el-button>
                    <el-button v-if="isEditing" type="success" @click="saveInfo">保存</el-button>
                    <el-button v-if="isEditing" type="warning" @click="cancelEdit">取消</el-button>
                    <el-button v-if="isEditing" type="info" @click="showPasswordDialog">修改密码</el-button>
                </div>
            </div>
        </div>
        <el-dialog title="修改密码" :visible.sync="dialogVisible" width="40%">
            <div class="dialog-content">
                <div class="info-item">
                    <div class="info-content">
                        <label>原密码:</label>

                        <input v-model="originalPassword" type="password" placeholder="请输入原密码" class="custom-input"
                            @blur="validateOriginalPassword" />
                    </div>
                    <div v-if="originalPasswordError" class="error-message">{{ originalPasswordError }}</div>

                </div>
                <div class="info-item">
                    <div class="info-content">
                        <label>新密码:</label>

                        <input v-model="newPassword" type="password" placeholder="请输入新密码" class="custom-input"
                            @blur="validateNewPasswords" />
                    </div>
                    <div v-if="newPasswordError" class="error-message">{{ newPasswordError }}</div>

                </div>
                <div class="info-item">
                    <div class="info-content">
                        <label>确认新密码:</label>

                        <input v-model="confirmNewPassword" type="password" placeholder="请确认新密码" class="custom-input"
                            @blur="validateNewPasswords" />
                    </div>
                    <div v-if="confirmNewPasswordError" class="error-message">{{ confirmNewPasswordError }}</div>

                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirmPasswordChange">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
    export default {
        name: 'personInfo',
        data() {
            return {
                userInfo: {
                    avatar: '', // 默认头像地址
                    username: '', // 用户名
                    name: '', // 姓名
                    password: '', // 密码
                    email: '', // 邮箱
                    mobile: '' // 手机号
                },
                isEditing: false,
                originalInfo: {},
                originalPassword: '',
                newPassword: '',
                confirmNewPassword: '',
                showPasswordFields: false,
                dialogVisible: false,

                originalPasswordError: '',
                newPasswordError: '',
                confirmNewPasswordError: ''
            };
        },
        created() {
            this.getUserInfo();
        },
        methods: {
            async getUserInfo() {
                try {
                    const res = await this.$http.get('/sys/sysUser/getNowUser');
                    this.userInfo = res.data.data;
                    this.originalInfo = { ...this.userInfo }; // 备份原始数据
                    if (!this.userInfo.avatar) {
                        this.userInfo.avatar = require('@/assets/images/user1.png');
                    }
                } catch (error) {
                    console.error('Error fetching user info:', error);
                }
            },
            changeAvatar() {
                if (this.isEditing) {
                    this.$refs.avatarInput.click();
                }
            },
            async onFileChange(e) {
                console.log('file', e)
                const file = e.target.files[0];
                if (file) {
                    this.$message.info('正在上传...');
                    let avatarUrl = await this.uploadAvatar(file);
                    const filename = avatarUrl.split("//").pop()
                    console.log('filename', filename);

                    avatarUrl = `http://so9r96ky6.hn-bkt.clouddn.com/images//${filename}`;
                    this.$message.success('上传成功');
                    if (avatarUrl) {
                        console.log('avatarUrl', avatarUrl);
                        this.userInfo.avatar = avatarUrl;
                    }
                }
            },
            async uploadAvatar(file) {
                const formData = new FormData();
                formData.append('file', file);

                try {
                    const response = await this.$http.post('/upload', formData);
                    console.log('response', response.data)
                    if (response.data.code == 'SUCCESS') {
                        console.log('response', response.data.data)
                        return response.data.data; // 假设后端返回的JSON格式为 { url: "http://example.com/path/to/image.jpg" }
                    } else {
                        throw new Error('Failed to upload image');
                    }
                } catch (error) {
                    console.error('Error uploading image:', error);
                    this.$message.error('图片上传失败');
                    return null;
                }
            },
            toggleEdit() {
                this.isEditing = true;
            },
            cancelEdit() {
                this.isEditing = false;
                this.userInfo = { ...this.originalInfo }; // 恢复原始数据
                this.originalPassword = '';
                this.newPassword = '';
                this.confirmNewPassword = '';
                this.showPasswordFields = false;

                this.originalPasswordError = '';
                this.newPasswordError = '';
                this.confirmNewPasswordError = '';
            },
            async validateOriginalPassword() {
                if (!this.originalPassword) {
                    this.originalPasswordError = '原密码不能为空';
                    return;
                }

                try {
                    const response = await this.$http.post('/sys/sysUser/validatePassword', {

                        username: this.userInfo.username,
                        password: this.originalPassword
                    }
                    );

                    if (response.data.data === true) {
                        this.originalPasswordError = '';
                    } else {
                        this.originalPasswordError = '原密码不正确';
                    }
                } catch (error) {
                    console.error('验证原密码失败:', error);
                    this.originalPasswordError = '验证原密码失败';
                }
            },
            validateNewPasswords() {
                if (this.newPassword && this.confirmNewPassword) {
                    if (this.newPassword === this.confirmNewPassword) {
                        this.newPasswordError = '';
                        this.confirmNewPasswordError = '';
                    } else {
                        this.newPasswordError = '';
                        this.confirmNewPasswordError = '新密码和确认新密码不一致';
                    }
                } else {
                    this.newPasswordError = '';
                    this.confirmNewPasswordError = '';
                }
            },

            saveInfo() {


                const userInfo = {
                    ...this.userInfo,
                    password: undefined // 或者直接删除这个字段
                };


                this.$http.post('/sys/sysUser/update', userInfo)
                    .then(response => {
                        this.$message.success('信息已保存');
                        this.isEditing = false;
                        this.originalInfo = { ...this.userInfo }; // 更新备份数据
                        this.originalPassword = '';
                        this.newPassword = '';
                        this.confirmNewPassword = '';
                        this.showPasswordFields = false;

                        this.originalPasswordError = '';
                        this.newPasswordError = '';
                        this.confirmNewPasswordError = '';
                    })
                    .catch(error => {
                        console.error('保存信息失败:', error);
                        this.$message.error('保存信息失败');
                    });
            },
            showPasswordDialog() {
                this.dialogVisible = true;
            },
            submitPassword() {
                const userInfo = {
                    ...this.userInfo,
                    password: this.newPassword // 或者直接删除这个字段
                };


                this.$http.post('/sys/sysUser/update', userInfo)
                    .then(response => {
                        this.$message.success('密码已修改');


                    })
                    .catch(error => {
                        console.error('修改密码失败:', error);
                        this.$message.error('修改密码失败');
                    });
            },
            confirmPasswordChange() {
                console.log('确认修改密码', this.originalPasswordError, this.newPasswordError);
                if (this.originalPasswordError != '') {
                    this.$message.error('原始密码不正确');


                    return;
                }
                if (this.newPasswordError != '') {
                    this.$message.error('请正确输入新密码');

                    return;
                }
                this.submitPassword()
                this.dialogVisible = false;
                this.showPasswordFields = true;

                // 清空密码输入框内容
                this.originalPassword = '';
                this.newPassword = '';
                this.confirmNewPassword = '';
            }
        }
    };
</script>
<style scoped lang="less">
    .person-info-container {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        background-color: #f4f4f9;
        padding: 20px;

        .person-info-card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 400px;

            .avatar-container {
                text-align: center;
                margin-bottom: 20px;

                .avatar {
                    width: 150px;
                    height: 150px;
                    border-radius: 50%;
                    cursor: pointer;
                    transition: transform 0.2s;
                }

                .avatar:hover {
                    transform: scale(1.05);
                }
            }

            .info-container {
                .info-item {
                    display: flex;
                    align-items: center;

                    margin-bottom: 15px;

                    label {
                        font-size: 16px;
                        color: #333;
                        width: 100px;
                        text-align: right;
                        margin-right: 10px;
                    }

                    .right-align {
                        text-align: right;
                        color: #333;
                        flex-grow: 1;
                    }

                    .custom-input {
                        border: none;
                        font-size: 16px;
                        border-bottom: 1px solid #ccc;
                        padding: 5px 0;
                        box-sizing: border-box;
                        transition: border-color 0.2s;
                        color: #333;
                        text-align: right;
                        flex-grow: 1;

                        &:focus {
                            border-color: #007bff;
                            outline: none;
                        }
                    }

                    .error-message {
                        color: red;
                        font-size: 14px;
                        margin-top: 5px;
                        flex-grow: 1;
                    }
                }

                .button-group {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    width: 250px;
                    margin: 0 auto;
                    margin-top: 60px;

                    .el-button {
                        width: 100px;
                        margin: 0 5px;
                    }
                }
            }
        }

        .dialog-footer {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .dialog-content {
            .info-item {
                display: flex;
                align-items: center;
                justify-content: center;

                flex-direction: column;
                margin-bottom: 10px;

                label {
                    font-size: 16px;
                    color: #333;
                    width: 100px;

                    margin-right: 10px;
                }

                .info-content {
                    display: flex;
                    justify-content: center;


                }

                .right-align {
                    text-align: right;
                    color: #333;
                    flex-grow: 1;
                }

                .custom-input {
                    border: none;
                    border-bottom: 1px solid #ccc;
                    padding: 5px 0;
                    box-sizing: border-box;
                    transition: border-color 0.2s;
                    color: #333;


                    &:focus {
                        border-color: #007bff;
                        outline: none;
                    }
                }

                .error-message {
                    color: red;
                    margin-left: 20px;
                    font-size: 10px;

                }
            }
        }
    }
</style>