// pages/analyse/analyse.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    headInfo: {
      img: '/resources/common/img/menu-item-4.png',
      title: '保单分析',
    },
    article : [
      {
        id: 1,
        title: "终身危疾保险的精算评分(2017年9月)",
        thumb: "/resources/analyse/img/1.png",
        page: "/pages/analyse-detail/analyse-detail?index=0"
      },
      {
        id: 2,
        title: "【销售陷阱】误填财务需要分析买错保险难追究",
        thumb: "/resources/analyse/img/2.png",
        page: "/pages/analyse-detail/analyse-detail?index=1"
      },
      {
        id: 3,
        title: "【精读篇】10Life 的危疾评分方法",
        thumb: "/resources/analyse/img/3.png",
        page: "/pages/analyse-detail/analyse-detail?index=2"
      },
      {
        id: 4,
        title: "【储蓄寿险的盲点#3】有保障又有钱储，真系咁So？",
        thumb: "/resources/analyse/img/4.png",
        page: "/pages/analyse-detail/analyse-detail?index=3"
      },
      {
        id: 5,
        title: "【储蓄寿险的盲点#2】100% 红利实现率就冇得输？",
        thumb: "/resources/analyse/img/5.png",
        page: "/pages/analyse-detail/analyse-detail?index=4"
      },
      {
        id: 6,
        title: "【储蓄寿险的盲点#1】储蓄寿险的语言艺术",
        thumb: "/resources/analyse/img/6.png",
        page: "/pages/analyse-detail/analyse-detail?index=5"
      }
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})