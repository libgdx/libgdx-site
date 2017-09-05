var ListItem = function (listitem) {

	this.item = listitem;
	this.canHide = listitem.data("always-visible") == true ? false : true;
	this.document = listitem.text();


	this.show = function () {
		if (this.canHide) {
			this.item.show(); 
		}
	}

	this.hide = function () {
		if (this.canHide) {
			this.item.hide();
		}
	}	
}

var List = function (list) {
	this.parentList = $("#" + list);
	this.items = [];

	var self = this;

	var subtitle = this.parentList.find("p");
	subtitle.each(function (subtitle) {
		self.items.push(new ListItem($(this))); 
	});
	
	var links = this.parentList.find("a");
	links.each(function (link) {
		self.items.push(new ListItem($(this)));
	});
}

var search = function (searchFunction, list) {

	$.get("/documentation/documentation_index", function (data) {
		var jsonObject = JSON.parse(data);
		for (var i = 0; i < jsonObject.length; i++) {
			if (matches(searchFunction, jsonObject[i].tokens, jsonObject[i].document)) {
				for (var j = 0; j < list.items.length; j++) {
					if (list.items[j].document.toLowerCase() == jsonObject[i].document.toLowerCase()) {
						list.items[j].show();
					}
				}
			} else {
				for (var j = 0; j < list.items.length; j++) {
					if (list.items[j].document.toLowerCase() == jsonObject[i].document.toLowerCase()) {
						list.items[j].hide();
					}
				}
			}
		}
	});

}

var matches = function (searchString, tokens, articleName) {

    var found = articleName.toLowerCase().indexOf(searchString.toLowerCase());
    if (found != -1) { 
		return true;
	}

    for (var i = 0; i < tokens.length; i++) {
        found = tokens[i].toLowerCase().indexOf(searchString.toLowerCase());
        if (found != -1) {
			return true;
		}
    }

    return false;
}
