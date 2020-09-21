<template>
  <div class="login-section">
    <!-- rules 设置表单的验证规则 -->
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="top" label-width="100px"
             class="demo-ruleForm">
      <el-form-item label="用户名" prop="name">
        <el-input v-model="ruleForm.name" type="text"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="ruleForm.password" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {login} from '@/service/request';

export default {
  name: "Login",
  data() {
    return {
      ruleForm: {
        name: '',
        password: '',
      },
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    submitForm(ruleForm) {
      console.log(ruleForm);
      //注意这里要加async
      this.$refs[ruleForm].validate(async (valid) => {
        if (valid) {
          //请求接口 进行登录
          login({
            name: this.ruleForm.name,
            password: this.ruleForm.password
          }).then(res => {
            console.log(res);
            if (res.code === 0) {
              //存储token
              localStorage.setItem('token',res.data.token);
              //登录成功跳转到home页面
              this.$router.push('home');
            }else{
              this.$message.error(res.mes);
            }
          }).catch(error => {
            this.$message.error(error);
          });
        } else {
          this.$message.error('用户名或密码错误');
          return false;
        }
      });
    },
    resetForm(ruleForm) {
      this.$refs[ruleForm].resetFields();
    }
  }
}
</script>

<style lang="stylus" scoped>
.login-section
  padding 0 20px
</style>