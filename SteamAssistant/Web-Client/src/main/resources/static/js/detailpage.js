$(function () {
    //饼图
    var myChart1 = echarts.init(document.getElementById('line'));
    //词云
    var myChart2 = echarts.init(document.getElementById('wordcloud'));
    //柱状图
    //传输基本信息

    //传输折线图信息
    var dates=[]
    var pricebydate=[]
    myChart1.showLoading();
    $.ajax({
        async: false,
        type : 'get',
        url : "/DATA/game-History?historys="+historys,
        dataType : "json",
        success : function (result) {
            dates=historys.ChangeTime;
            pricebydate=historys.Price;
            myChart1.hideLoading();

        },
        error : function(errorMessage){
            alert("Data is Error.age");
            myChart1.hideLoading();
        }
    });
    //传输词云
    let data = {
        value: [],
        image: "data:image/png;base64,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"

    };

    $.ajax({
        async: false,
        type : 'get',
        url : "/DATA/game-Comment?Comments="+Comments,
        dataType : "json",
        success : function (result) {
            data.value=result;
            myChart2.hideLoading();
        },
        error : function(errorMessage){
            alert("Data is Error.comment");
            myChart2.hideLoading();
        }
    });
    //传输地图

    myChart1.setOption(option = {
        backgroundColor: '#080b30',
        title: {
            text: '近日价格走向'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                lineStyle: {
                    color: {
                        type: 'cross',
                        x: 0,
                        y: 0,
                        x2: 0,
                        y2: 1,
                        colorStops: [{
                            offset: 0,
                            color: 'rgba(0, 255, 233,0)'
                        }, {
                            offset: 0.5,
                            color: 'rgba(255, 255, 255,1)',
                        }, {
                            offset: 1,
                            color: 'rgba(0, 255, 233,0)'
                        }],
                        global: false
                    }
                },
            },
        },
        xAxis: {
            data:pricebydate
        },
        yAxis: {
            splitLine: {
                show: false
            }
        },
        toolbox: {
            left: 'center',
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                restore: {},
                saveAsImage: {}
            }
        },
        visualMap: {
            top: 10,
            right: 10,
            pieces: [{
                gt: 0,
                lte: 50,
                color: 'rgba(173,193,234,0.95)'
            }, {
                gt: 50,
                lte: 100,
                color: '#bfeae3'
            }, {
                gt: 100,
                lte: 150,
                color: '#bfeac5'
            }, {
                gt: 150,
                lte: 200,
                color: '#e9eabf'
            }, {
                gt: 200,
                lte: 300,
                color: '#ead5bf'
            }, {
                gt: 300,
                color: '#ecbcb7'
            },{
                gt: 350,
                color: '#e8aaca'
            }
            ],
            outOfRange: {
                color: '#d2b6ec'
            }
        },
        series: {
            name: '价格',
            type: 'line',
            data: dates,
            markLine:{
                silent: true,
                data: [{
                    yAxis: 50
                }, {
                    yAxis: 100
                }, {
                    yAxis: 150
                }, {
                    yAxis: 200
                }, {
                    yAxis: 300
                }, {
                    yAxis: 350
                }]
            }

        }
    });
    var maskImage = new Image();
    maskImage.src = data.image
    maskImage.onload = function(){
        myChart2.setOption( {
            title : {
                text: '词云',
                x:'left',
                padding:[15,20]
            },
            tooltip: {},
            series: [{
                type: 'wordCloud',
                gridSize: 2,
                sizeRange: [12, 50],
                rotationRange: [-90, 90],
                shape: 'pentagon',
                width: 600,
                height: 400,
                maskImage: maskImage,
                drawOutOfBound: false,
                textStyle: {
                    normal: {
                        color: function() {
                            return 'rgb(' + [
                                Math.round(Math.random() * 260),
                                Math.round(Math.random() * 260),
                                Math.round(Math.random() * 260)
                            ].join(',') + ')';
                        }
                    },
                    emphasis: {
                        shadowBlur: 10,
                        shadowColor: '#333'
                    }
                },
                data: data.value
            }]
        })
    }
/*    //测试
    /!*data = [[108.479, 23.1152, 1100]];
    let option = {
        backgroundColor: "#000",
        globe: {
            baseTexture: "img/world.jpg",
            heightTexture: "img/world.jpg",
            displacementScale: 0.04,
            environment: "img/starfield.jpg",
            shading: "realistic",
            realisticMaterial: {
                roughness: 0.9
            },
            postEffect: {
                enable: true
            },
            light: {
                main: {
                    intensity: 5,
                    shadow: true
                },
                ambientCubemap: {
                    texture: "img/pisa.hdr",
                    diffuseIntensity: 0.2
                }
            }
        },
        series: {
            type: 'bar3D',
            coordinateSystem: 'globe',
            label: {
                show: true,
                //distance:5,
                formatter: '我在：{c}',
                textStyle: {
                    fontWeight: 'bolder',
                    fontSize: 22,
                    lineHeight: 10,
                    backgroundColor: "#fff",
                    padding: [5, 15],
                    borderRadius: 5
                }

            },
            itemStyle: {
                color: "rgb(252,158,158)"
            },
            silent: false,//图形是否不响应和触发鼠标事件
            shading: "realistic",//着色效果
            data: data
        }
    };
        myChart3.setOption(option, true);*!/*/


})