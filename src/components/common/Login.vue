<template>
    <div class="login_container">
        <div class="login_form">
            <p class="login_title">金威报价系统</p>
            <el-form :model="form" :rules="rules" status-icon label-width="100px" class="demo-ruleForm" ref="loginForm">
                <el-form-item label="账号" prop="username">
                    <el-input v-model="form.username" placeholder="请输入账号"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
                </el-form-item>


            </el-form>

            <el-button type="primary" size="medium" @click="submitLoginForm">登录</el-button>
            <!-- <el-button type="info" size="medium" @click="goToRegister">注册</el-button> -->
        </div>
    </div>
</template>
<script>
    export default {
        name: "Login",
        data() {
            return {
                form: {
                    username: "",
                    password: "",
                }, rules: {
                    username: [
                        { required: true, message: '请输入账号', trigger: 'blur' }
                    ], password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            submitLoginForm() {
                this.$refs['loginForm'].validate((valid) => {
                    if (valid) {

                        this.$http.post('/login', this.form).then(res => {
                            console.log(res)

                            if (res.data.code === 200) {
                                sessionStorage.setItem('token', res.headers.authorization)
                                sessionStorage.setItem('username', this.form.username)
                                this.$router.push('/')
                            } else {
                                this.$message.error(res.data.msg)
                            }



                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            goToRegister() {
                this.$router.push('/register');
            }
        }
    };
</script>
<style lang="less" scoped>
    .login_container {
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

        .login_form {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            width: 510px;
            margin: 0px auto;
            padding: 0 55px 15px 35px;
            background-color: #fff;
            border: none;
            border-radius: 5px;

            box-shadow: 0 0 25px #cac6c6;

            .login_title {
                font-family: '微软雅黑 Bold', '微软雅黑';
                font-weight: 700;
                text-decoration: none;
                color: rgb(0, 121, 254);
                font-size: 32px;
                margin-top: 50px;
                margin-bottom: 30px;
                text-align: center;
            }

            .demo-ruleForm {
                width: 450px;
                margin-right: 20px;
            }
        }
    }
</style>