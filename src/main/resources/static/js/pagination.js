function Pagination(obj) {
    this.id = obj.id;  //div id
    this.url = obj.url;
    this.pageSize = obj.pageSize;
    this.pageNum = 1; //current page number
    this.total = 0; //total count
    this.totalPage = 0;
    this.barSize = obj.barSize; //分页工具条上展现的页码数
    this.numPoint=1;
    this.data = obj.data;
    this.success = obj.success;
    this.error = obj.error;
    this.div = null;
    this.init();
}
Pagination.prototype.init = function () {
    if (this.data == undefined) {
        this.data = {}
    }
    this.div = $('#' + this.id);
    this.fetchData(1);
};

Pagination.prototype.fetchData = function (pageNum) {
    this.data.pageNum = pageNum;
    this.data.pageSize = this.pageSize;
    var that = this;
    $.ajax({
        url: that.url,
        data: that.data,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if(data.total==undefined) {
                that.success(data.rows);
                return;
            }
            that.total = data.total;
            var tmp = that.total % that.pageSize;
            var num = Math.floor(that.total / that.pageSize);
            that.totalPage = tmp == 0 ? num : num + 1;
            that.showUI();
            that.success(data.rows);
        },
        error: function (msg) {
            that.error(msg);
        }
    })
};
Pagination.prototype.showUI = function () {
    var that = this;
    this.div.empty();
    var currentBarSize=this.totalPage-(this.numPoint-1)*this.barSize;
    currentBarSize = currentBarSize > this.barSize ? this.barSize : currentBarSize;
    this.div.append('<a class="item"><i class="left arrow icon"></i> 上一页</a>');
    for (var i = 0; i < currentBarSize; i++) {
        this.div.append('<a class="item">' + (this.pageNum+i) + '</a>');
    }
    this.div.append('<a class="item"> 下一页 <i class="icon m-right arrow"></i></a>');

    var array = this.div.find('a');
    for (var j = 0; j < array.length; j++) {
        var current = $(array[j]);
        if (j == 0) {
            current.click({param: that}, that.previewPage);
        } else if (j == array.length - 1) {
            current.click({param: that}, that.nextPage)
        } else {
            current.click({param: that}, function (event) {
                var p = event.data.param;
                var n = $(this).text().trim();
                p.fetchData(parseInt(n));
            })
        }
    }
};
Pagination.prototype.nextPage = function (event) {
    var that = event.data.param;
    if (that.numPoint > 1) {
        that.div.find('a').first().attr('class', 'item');
    }
    if (that.numPoint >= Math.ceil(that.totalPage/that.barSize)) {
        that.div.find('a').last().attr('class', 'disabled item');
    } else {
        that.pageNum=that.numPoint*that.barSize+1;
        that.numPoint++;
        that.showUI();
    }
};
Pagination.prototype.previewPage = function (event) {
    var that = event.data.param;
    if (that.numPoint <Math.ceil(that.totalPage/that.barSize)) {
        that.div.find('a').last().attr('class', 'item');
    }
    if (that.numPoint == 1) {
        that.div.find('a').first().attr('class', 'disabled item');
    } else {
        that.numPoint--;
        that.pageNum=(that.numPoint-1)*that.barSize+1;
        that.showUI();
    }
};