$(function(){
	$('.load-more').on('click',function(){
		const btn = $(this);
		const loader =  btn.find('span');
		$.ajax({
			url: '/posts.html',
			type: 'GET',
			beforeSend: function(){
				btn.attr('disabled',true);
				loader.addClass('d-line-block');
			},

			success: function(responce){
				setTimeout(function(){
					loader.removeClass('d-line-block');
					btn.attr('disabled', false);
					$('.after-posts').before(responce);
				}, 1000);
			},

			error: function(){
				alert('Error!');
				loader.removeClass('d-line-block');
					btn.attr('disabled', false);
			}

		});
	});
});