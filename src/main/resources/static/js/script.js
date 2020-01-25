// Scroll to current day
// const currentDay = document.getElementById('currentDay');
// currentDay.scrollIntoView({
//     inline: 'center',
// });

// Names of days in week
var daysNames = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

// Get days in month from today
// function getDaysInMonthFromToday(month, year) {
//     var date = new Date(year, month, new Date().getDate());
//     var days = [];
//     while (date.getMonth() === month) {
//         days.push(new Date(date));
//         date.setDate(date.getDate() + 1);
//     }
//     return days;
// }
// var daysInMonthFromToday = getDaysInMonthFromToday(new Date().getMonth(), new Date().getFullYear());

// Format current month days range
// function getFormattedDateRange() {
//     return daysInMonthFromToday[0].getDate() + "." +
//         daysInMonthFromToday[0].getMonth() + 1 + "." +
//         daysInMonthFromToday[0].getFullYear() + " - " +
//         daysInMonthFromToday[daysInMonthFromToday.length - 1].getDate() + "." +
//         daysInMonthFromToday[daysInMonthFromToday.length - 1].getMonth() + 1 + "." +
//         daysInMonthFromToday[daysInMonthFromToday.length - 1].getFullYear();
// }
// document.getElementById("dateRange").innerText = getFormattedDateRange();

// Fill table with remaining days in month
// document.getElementById("days").appendChild(document.createElement("tr"));
// for (var i = 0; i < daysInMonthFromToday.length; i++) {
//     var dayInMonth = daysInMonthFromToday[i].getDate();
//     var dayInWeek = daysInMonthFromToday[i].getDay();
//     var dayInSchedule = document.createElement("th");
//     dayInSchedule.innerHTML = daysNames[dayInWeek] + "<div>" + dayInMonth + "</div>";
//     document.getElementById("days").childNodes[0].appendChild(dayInSchedule);
// }

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
