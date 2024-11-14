<template>
    <div class="register_container">
        <div class="register_form">
            <p class="register_title">金威报价系统 - 注册</p>
            <el-form :model="form" :rules="rules" status-icon label-width="100px" class="demo-ruleForm" ref="registerForm">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input type="password" v-model="form.confirmPassword" placeholder="请再次输入密码"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="medium" @click="submitRegisterForm">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    name: "Register",
    data() {
        return {
            form: {
                username: "",
                email: "",
                password: "",
                confirmPassword: ""
            },
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                email: [
                    { required: true, message: '请输入邮箱', trigger: 'blur' },
                    { type: 'email', message: '请输入有效的电子邮件地址', trigger: ['blur', 'change'] }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 6, max: 16, message: '密码长度应在6到16个字符之间', trigger: 'blur' }
                ],
                confirmPassword: [
                    { required: true, message: '请再次输入密码', trigger: 'blur' },
                    { validator: this.validatePassword, trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        validatePassword(rule, value, callback) {
            if (value !== this.form.password) {
                callback(new Error('两次输入的密码不一致'));
            } else {
                callback();
            }
        },
        submitRegisterForm() {
            this.$refs['registerForm'].validate((valid) => {
                if (valid) {
                    this.$http.post('/register', this.form).then(res => {
                        console.log(res);

                        if (res.data.code === 200) {
                            this.$message.success('注册成功，请登录');
                            this.$router.push('/');
                        } else {
                            this.$message.error(res.data.msg);
                        }
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        }
    }
};
</script>

<style lang="less" scoped>
.register_container {
    width: 100%;
    height: 100vh;
    background-color: rgba(242, 242, 242, 1);
    background-image: url(../../assets/login_bg.jpg);
    background-position: center bottom;
    background-repeat: no-repeat;
    background-size: 1278px 559px;
    border: none;
    border-radius: 0px;
    display: flex;
    justify-content: center;
    align-items: center;

    .register_form {
        width: 510px;
        margin: 0px auto;
        padding: 0 55px 15px 35px;
        background-color: #fff;
        border: none;
        border-radius: 5px;

        box-shadow: 0 0 25px #cac6c6;

        .register_title {
            font-family: '微软雅黑 Bold', '微软雅黑';
            font-weight: 700;
            text-decoration: none;
            color: rgb(0, 121, 254);
            font-size: 32px;
            margin-top: 50px;
            margin-bottom: 30px;
            text-align: center;
        }
    }
}
</style>