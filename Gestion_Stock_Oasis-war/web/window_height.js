
function window_height(id_component, percentage) {
    //console.log("Hauteur"+window.innerHeight);
    //alert("Hauteur"+window.innerHeight);
    var taille = ($(window).height() * percentage) / 100;
    $(id_component).css({height: taille + "px"});
    console.log("Taille"+ taille + "px");

    //id_component.css({height : taille});

    //var el = document.getElementById(id_component);
    //el.setAttribute("class", "sub-menu active");
    //el.css({height: taille + "px"});

}
