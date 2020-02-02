function getDaysInMonthFromToday(month, year) {
    var date = new Date(year, month, new Date().getDate());
    var days = [];
    while (date.getMonth() === month) {
        days.push(new Date(date));
        date.setDate(date.getDate() + 1);
    }
    return days;
}
var daysInMonthFromToday = getDaysInMonthFromToday(new Date().getMonth(), new Date().getFullYear());

function getFormattedDateRange() {
    return daysInMonthFromToday[0].getDate() + "." +
        daysInMonthFromToday[0].getMonth() + 1 + "." +
        daysInMonthFromToday[0].getFullYear() + " - " +
        daysInMonthFromToday[daysInMonthFromToday.length - 1].getDate() + "." +
        daysInMonthFromToday[daysInMonthFromToday.length - 1].getMonth() + 1 + "." +
        daysInMonthFromToday[daysInMonthFromToday.length - 1].getFullYear();
}

// Get date for date-time picker's min and max values
function getDateForDateTimePicker(i) {
    var result = "";
    result += daysInMonthFromToday[i].getFullYear() + "-" +
    daysInMonthFromToday[i].getMonth() + 1 + "-";

    if(daysInMonthFromToday[i].getDate().toString().length == 1)
        result += "0" + daysInMonthFromToday[i].getDate();
    else
        result += daysInMonthFromToday[i].getDate();
    
    result += "T00:00:00";

    return result;
}

document.getElementById("start-date-time").setAttribute("min", getDateForDateTimePicker(0));
document.getElementById("end-date-time").setAttribute("min", getDateForDateTimePicker(0));
