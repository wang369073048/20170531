/**
 * 
 */
function isArray(a) {
    return (a.constructor.toString().match(/^function\ Array\(\)/gm) != null);
}

var ArrayList = function() {
	var args = ArrayList.arguments;
	var list = new Array(); 

	if (args != null && args.length > 0) {
		if(isArray(args[0])){
			list = args[0];
		}
	}

	/**  
	 * 添加  
	 * @param {Object} object  
	 */  
	this.add = function(object) {  
	    list[list.length] = object;  
	};
	
	/**
	 * 得到指定元素在此列表中的位置。
	 */
	this.indexOf = function(object) {
		for ( var i = 0; i < list.length; i++) {
			if (list[i] == object) {
				return i;
			}
		}
		return -1;
	};
	
	/**
	 * 判断是否包含此元素。
	 */
	this.contains = function (object) {
		return this.indexOf(object) >= 0;
    };
	  
	/**
	 * 移除此列表中指定位置上的元素。
	 * 
	 * @param index
	 *            指定位置
	 * @return 此位置的元素
	 */  
	this.removeAt = function(index) {  
	    var object = list[index];  
	    list.splice(index, 1);  
	    return object;  
	};
  
	/**   
	 * 移除此列表中指定元素。   
	 * @param object 指定元素   
	 * @return 此位置的元素   
	 */  
	this.remove = function(object) {  
	    var i = 0;  
	    for (; i < list.length; i++) {  
	        if (list[i] === object) {  
	            break;  
	        }  
	    }  
	    if (i >= list.length) {  
	        return null;  
	    } else {  
	        return this.removeAt(i);  
	    }  
	};
	  
	/**   
	 * 获得列表中指定元素。   
	 * @param object 指定元素   
	 * @return 此位置的元素   
	 */  
	this.get = function(index) {    
	    return list[index];    
	};
	  
	/**   
	 * 移除此列表中的所有元素。   
	 */    
	this.removeAll = function() {    
	    list.splice(0, list.length);    
	};
	  
	/**   
	 * 返回此列表中的元素数。   
	 * @return 元素数量   
	 */    
	this.size = function() {    
	    return list.length;    
	};
	     
	    
	/**   
	 *  如果列表不包含元素，则返回 true。   
	 * @return true or false   
	 */    
	this.isEmpty = function() {    
	    return list.length == 0;    
	};
};