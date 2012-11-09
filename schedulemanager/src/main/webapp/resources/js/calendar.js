$(function() {
	$('.hour').sortable({
		connectWith : '.hour',
		start: function(e, ui){
	        ui.placeholder.width(ui.item.width());
	    }
	});
	$('.hour').disableSelection();
	
	
	
	for (var i = 0; i < sections.courseSlots.length; i++) {
		var event = $('<div/>', {
		    class : 'event',
		    width : setDuration(sections.courseSlots[i]),
		}).appendTo(findId(sections.courseSlots[i]));
		
		$('<div/>', {
		    id: sections.courseSlots[i].nrc,
		    title: sections.courseSlots[i].acronym,
		    class : 'event-name',
		    text : sections.courseSlots[i].acronym,
		}).appendTo(event);
	}

	$('.event').resizable({
		minWidth: 90,
		maxHeight: 25,
		minHeight: 25,
		grid: 100
	});
	
	$('.event').dblclick(function() {
		
		var nrc = $(this).children().attr('id');
		var url = "http://localhost:8080/schedulemanager/schedule/editsection/2011-2012-Automne-1/2011-2012/Automne/90111";
		
		$.get(url, function(data) {

				$('#editCourse').find('.modal-body').html(data);
				$('#editCourse').modal('show');
			});
	});

	$('.event').tooltip();
});

function findId(cs) {
	return ('#'+ cs.dayOfWeek.toLowerCase().substring(0,3) + cs.timeSlotStart.split(':')[0] + minutes(cs));
}

function minutes(cs) {
	var minutes = cs.timeSlotStart.split(":")[1];
	if (minutes == 30) {
		return 5;
	}
	return 0;
}

function setDuration(cs) {
	return (cs.duration * 100) + 'px';
}