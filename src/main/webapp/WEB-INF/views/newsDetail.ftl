
<body>
<iframe src="${newsUrl}" style = "width:100%;margin-top: 50px;height: 605px;">
</iframe>
<ul id="getText"></ul>
<script>
    function dosome(text){
        document.getElementById("getText").innerHTML= decodeURI(text);    
    }
</script>
</body>
