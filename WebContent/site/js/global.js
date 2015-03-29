
var iPad = navigator.userAgent.match(/iPad/i) != null;

if (iPad) {
    $('body').addClass('ipad');
}

(function( $ ){
    var checkSize = function() {
    var current_width = $(window).width();
    var tabletWidth = 600;
    var desktopWidth = 1000;

    if(current_width < tabletWidth)
      $('html, body').addClass("mobile").removeClass("tablet").removeClass("desktop").data('device', 'mobile');
    else if(current_width >= tabletWidth && current_width <= desktopWidth)
      $('html, body').removeClass("mobile").addClass("tablet").removeClass("desktop").data('device', 'tablet');
    else
      $('html, body').removeClass("mobile").removeClass("tablet").addClass("desktop").data('device', 'desktop');
  }

  $(document).ready(checkSize);
  $(window).resize(checkSize);
  
})( jQuery );


$(document).ready(function() {
	
	
	
	$('.mobile-menu').click(function(e) {
        e.preventDefault();

        $(this).toggleClass('open-menu');
        $('.main-menu').toggleClass('active');

        if ($(this).hasClass('open-menu')) {
            $('.mobile-account').removeClass('open-account');
            $('.mobile-account-access').removeClass('active');
        }
    });

    $('.mobile-account').click(function(e) {
        e.preventDefault();

        $(this).toggleClass('open-account');
        $('.mobile-account-access').toggleClass('active');

        if ($(this).hasClass('open-account')) {
            $('.mobile-menu').removeClass('open-menu');
            $('.main-menu').removeClass('active');
        }
    });
	
	
	
	
	
    
		
	$('.slider').bxSlider({
		mode:         'fade',
		nextSelector: '#next',
		nextText:      '<i class="fa fa-caret-right">',
		prevSelector: '#prev',
		prevText:     '<i class="fa fa-caret-left">',
		infiniteLoop:  true,
		//pager:         false,
		auto:          true,
	});
	
	
	//login ---------------------------------------------------------------------------
	
	$('#btn-login').click(function(e){
		$('.box-login').addClass("in");
		$('.box-login').show("slow");
		e.preventDefault();	
	});
	
	$('.box-login > .close').click(function(e){
		e.preventDefault();
		$('.box-login').show("hide");
	});


    // A à Z ---------------------------------------------------------------------------
    $('#az-search').bind('keyup', function(e){
        var $this = $(this),
            itens = $('.indice-list > li'),
            term = slugfy($this.val().toLowerCase());

        itens.each(function(){
            var $item = $(this),
                title = slugfy($item.find('a').text().toLowerCase());

            if (title.indexOf(term) >= 0) {
                $item.removeClass('invisible');
            } else {
                $item.addClass('invisible');
            }
        });

        $('.indice-letter-group').each(function(){
            var $list = $(this);

            if ($list.find('.indice-list > li.invisible').length < $list.find('.indice-list > li').length) {
                $list.show();
            } else {
                $list.hide();
            }
        });
    });
	
	
	
	// abas --------------------------------------------------------------------------------------
	
	$("#tab-container").easytabs();
	
	// validação ----------------------------------------------------------------------------------
	
	$("#login").validate();
	$("#login2").validate();
	
	// Maskaras ----------------------------------------------------------------------------------
	
	$('.time').mask('00:00:00');
	$('.date').mask('00/00/0000');
	$('.date_time').mask('00/00/0000 00:00:00');
	$('.cep').mask('00000-000');
	$('.phone_with_ddd').mask('(00) 0000-0000');
	$('.phone_us').mask('(000) 000-0000');
	$('.mixed').mask('AAA 000-S0S');
	$('.cpf').mask('000.000.000-00', {reverse: true});
	$('.cnpj').mask('00.000.000/0000-00', {reverse: true});
	$('.money').mask('000.000.000.000.000,00', {reverse: true});
	
	var masks = ['(00) 00000-0000', '(00) 0000-00009'];
	$('.phone').mask(masks[1], {onKeyPress: 
   		function(val, e, field, options) {
       		field.mask(val.length > 14 ? masks[0] : masks[1], options);
   		}
	});

	
	
});

