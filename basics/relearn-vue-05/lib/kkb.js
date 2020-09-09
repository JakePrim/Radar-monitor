// export default function(_Vue,options){

// }

export default {
    install(_Vue,options){
        _Vue.prototype.getData=function(){
            console.log('getData 获取数据');
        }
    }
}