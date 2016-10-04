var randomData;

$('#randomDataChart').highcharts({
  chart : {
    type : 'line',
    events : {
      load : function() {
        randomData = this.series[0];
      }
    }
  },
  title : {
    text : false
  },
  xAxis : {
    type : 'datetime',
    minRange : 60 * 1000
  },
  yAxis : {
    title : {
      text : false
    },
    max: 100
  },
  legend : {
    enabled : false
  },
  plotOptions : {
    series : {
      threshold : 0,
      marker : {
        enabled : false
      }
    }
  },
  series : [ {
    name : 'Data',
      data : [ ]
    } ]
});

var socket = new SockJS('/stomp');
var client = Stomp.over(socket);

client.connect({}, function(frame) {

  client.subscribe("/topic/message", function(message) {
	//console.log("randomData = "+ randomData); 
	console.log("message.body = "+ message.body); 
    var point = [ (new Date()).getTime(), parseInt(message.body) ];
    var shift = randomData.data.length > 60;
    randomData.addPoint(point, true, shift);
  });

});