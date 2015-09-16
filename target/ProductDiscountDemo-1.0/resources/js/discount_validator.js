//$('#discount').bind('input', function() { 
//    parseInt($(this).val())/100*$('#originalPrice') // get the current value of the input field.
//    document.getElementById("validateprice").innerHTML = "Paragraph changed!";
//});

var discount = document.querySelector('#discount');

var originalprice = document.querySelector('#originalPrice');
var newprice = document.querySelector('#validatePrice');

function newPrice(){
    var calculatedprice = originalPrice.value * (100 - discount.value) / 100;
    newprice.textContent = calculatedprice;
}
