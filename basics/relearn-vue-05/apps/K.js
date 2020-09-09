export default {
    template: `
        <div>
            K 组件
            <button @click="handleClick">点击</button>
        </div>
    `,
    methods:{
        handleClick(){
            this.getData();
        }
    }
}