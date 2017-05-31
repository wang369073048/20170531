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
	 * ���  
	 * @param {Object} object  
	 */  
	this.add = function(object) {  
	    list[list.length] = object;  
	};
	
	/**
	 * �õ�ָ��Ԫ���ڴ��б��е�λ�á�
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
	 * �ж��Ƿ������Ԫ�ء�
	 */
	this.contains = function (object) {
		return this.indexOf(object) >= 0;
    };
	  
	/**
	 * �Ƴ����б���ָ��λ���ϵ�Ԫ�ء�
	 * 
	 * @param index
	 *            ָ��λ��
	 * @return ��λ�õ�Ԫ��
	 */  
	this.removeAt = function(index) {  
	    var object = list[index];  
	    list.splice(index, 1);  
	    return object;  
	};
  
	/**   
	 * �Ƴ����б���ָ��Ԫ�ء�   
	 * @param object ָ��Ԫ��   
	 * @return ��λ�õ�Ԫ��   
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
	 * ����б���ָ��Ԫ�ء�   
	 * @param object ָ��Ԫ��   
	 * @return ��λ�õ�Ԫ��   
	 */  
	this.get = function(index) {    
	    return list[index];    
	};
	  
	/**   
	 * �Ƴ����б��е�����Ԫ�ء�   
	 */    
	this.removeAll = function() {    
	    list.splice(0, list.length);    
	};
	  
	/**   
	 * ���ش��б��е�Ԫ������   
	 * @return Ԫ������   
	 */    
	this.size = function() {    
	    return list.length;    
	};
	     
	    
	/**   
	 *  ����б�����Ԫ�أ��򷵻� true��   
	 * @return true or false   
	 */    
	this.isEmpty = function() {    
	    return list.length == 0;    
	};
};