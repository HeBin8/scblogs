<template>
  <div class="login">
    <div class="login-container">
      <h3>欢迎登录</h3>
      <div class="login-form">
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="80px"
          class="demo-ruleForm"
        >
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username" clearable></el-input>
          </el-form-item>

          <el-form-item label="密码:" prop="password">
            <el-input
              type="password"
              show-password
              v-model="ruleForm.password"
            ></el-input>
          </el-form-item>

          <el-form-item>
            <el-button
              type="danger"
              round
              @click="submitForm('ruleForm')"
              style="width: 420px"
              >立即登录</el-button
            >
            <!--<el-button @click="resetForm('ruleForm')">重置</el-button>-->
          </el-form-item>
        </el-form>
      </div>
      <div class="zhuce-p">
        <span class="span-before">没有账号？</span>
        <router-link to="/register"
          ><span class="span-after">注册</span></router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
import qs from "qs";

export default {
  name: "",
  data() {
    return {
      userMessage: '',
      avatarUrl: "",
      ruleForm: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 1, max: 16, message: "长度在 1 到 16 个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .post("/login/login", qs.stringify(this.ruleForm))
            .then((res) => {
              console.log(res);
              if (res.data.code == 200 && res.data.status == false) {
                this.$message({
                  showClose: true,
                  message: "用户名或密码错误",
                  type: "warning",
                });
              }
              if (res.data.code == 402) {
                this.$message({
                  showClose: true,
                  message: "用户名或密码错误",
                  type: "warning",
                });
              }
              if (res.data.code == 200 && res.data.status == true) {
                this.$message({
                  showClose: true,
                  message: "恭喜您，登录成功~",
                  type: "success",
                });
                localStorage.setItem("token", res.headers.token);
                this.userMessage = JSON.stringify(res.data.data);
                this.avatarUrl = res.data.data.avatarUrl;
                this.bus.$emit("userMessage", this.userMessage);
                this.$router.push({ path: "/" });
              }
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style scoped>
/*登录*/
h3 {
  text-align: center;
}
.login-container .login-form {
  margin-top: 20px;
}

.zhuce-p {
  display: flex;
  justify-content: center;
  margin-top: 10px;
  font-size: 14px;
  /*padding-left: 50px;*/
}
.zhuce-p .span-after {
  color: #fc5531;
  cursor: pointer;
}
</style>
