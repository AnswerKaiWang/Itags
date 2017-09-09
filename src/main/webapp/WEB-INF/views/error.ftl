<script type="text/javascript" src="${ctx}/js/jquery-1.11.3.js" ></script>
  <script type="text/javascript">
    alert('${errorMsg}');
    if('${uri}'=="/index"){
    	window.location.href="${ctx}/login";
    }else{
    	window.parent.location.href="${ctx}/login";
    }										
    
  </script>
