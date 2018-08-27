$(document).ready(main);

var cont = 1;

function main() {
    
    $('.menu_bar').click(function(){
        //$('nav').toggle();
        if(cont == 1) {
            $('nav').animate({
                left: '0'
            }); 
            cont = 0;
        }else {
            cont = 1;
            $('nav').animate({
                left: '-100%'
            });
        }
    });
    
};
