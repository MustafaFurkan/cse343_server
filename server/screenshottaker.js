var page = require('webpage').create();
var system = require('system');
var address = system.args[1];
var path = system.args[2];
//viewportSize being the actual size of the headless browser
page.viewportSize = { width: 200, height: 100 };
//the clipRect is the portion of the page you are taking a screenshot of
page.clipRect = { top: 0, left: 0, width: 200, height: 100 };
//the rest of the code is the same as the previous example
page.open(address, function() {
	page.render(path);
	phantom.exit();
});
