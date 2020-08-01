const app = new Vue({
    el:'#app',
    data:{
        message:'页面加载于'+new Date().toLocaleDateString(),
        see:true,
        todos:[
            {text:"学习前端"},
            {text:"学习vue"},
            {text:"整个牛逼的项目"}
        ]
    }
});

