$(function () {
    var data1={}
    $.ajax({
        type : 'get',
        url : '/remote/game-data',
        dataType : "json",
        success : function (result) {
            data1=result;
        },
        error : function (errorMessage) {
            alert("Data is Error.");
        }
    })
    const vm = new Vue({
        el: '#test',
        data:{
            startPage: 1,
            // 每页的数据
            pageSize: 10,
            pagesizearrray: [10, 20, 30, 40],
            totalNum: 0,
            tableData: [],
            prev: this.$t('personal.prev'), // 上一页
            next: this.$t('personal.next'), // 下一页
            games:[
                data1
            ]
        },
        methods: {
            // 初始页currentPage、初始每页数据数pagesize和数据data
            handleSizeChange(size) {
                this.pageSize = size
                this.handleUserList()
            },
            handleCurrentChange(startPage) {
                this.startPage = startPage
                this.handleUserList()
            },
            handleUserList() {　　// 这个是接口的调用
                getpos({
                    posDeviceId: '',
                    merchantNo: '',
                    startPage: this.startPage,
                    pageSize: this.pageSize,
                    innerMain: 2,
                }).then((res) => {
                    // console.log(res, '表格数据')
                    if (res.code === '000000') {
                        this.loading = false
                        if (res.data) {
                            this.tableData = res.data.list　　　　　　//分页处理　　
                            this.totalNum = res.data.totalCount
                            this.totalCount = res.data.totalCount
                        } else {
                            this.tableData = []
                            this.totalCount = 0
                        }
                    }
                })
            },
        }
    });
    new Vue().$mount('#test2')
    new Vue().$mount('#test3')

})