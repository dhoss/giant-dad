<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <title>Giant Dad Workout Tracker - Track Workouts</title>
    <style>

        html, body {
            margin: 0;
            padding: 0;
            font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
            font-size: 14px;
        }

        #calendar {
            max-width: 1100px;
            margin: 40px auto;
        }

    </style>

    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.20/index.global.min.js'></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css' rel='stylesheet'>
    <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/bootstrap5@6.1.20/index.global.min.js'></script>
    <script>

        document.addEventListener('DOMContentLoaded', function () {
            const modal = document.querySelector("#view-workout-modal");
            const closeBtn = document.querySelector("#view-workout-modal-close");

            closeBtn.addEventListener("click", () => modal.close());

            var calendarEl = document.getElementById('calendar');

            var calendar = new FullCalendar.Calendar(calendarEl, {
                timeZone: 'UTC',
                themeSystem: 'bootstrap5',
                headerToolbar: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
                },
                weekNumbers: true,
                dayMaxEvents: true, // allow "more" link when too many events
                events: '/api/programs',
                dateClick: function(info) {
                    document.getElementById('view-workout-modal-day').innerHTML = info.dateStr;
                    modal.showModal();
                }
            });

            calendar.render();

        });

    </script>
</head>
<body>
<main>
    <div class="d-flex justify-content-center">
    <div id="calendar"></div>
    <dialog id="view-workout-modal">
        <h2>Workout</h2>
        <div id="view-workout-modal-day"></div>
        <div class="row">
            <div class="col-12">
                <label for="lift-data-list" class="form-label">Select lift</label>
                <input class="form-control" list="lift-data-list-options" id="lift-data-list" placeholder="Type to search">
                <datalist id="lift-data-list-options">
                    <option value="San Francisco"></option>
                    <option value="New York"></option>
                    <option value="Seattle"></option>
                    <option value="Los Angeles"></option>
                    <option value="Chicago"></option>
                </datalist>
            </div>
            <div class="col">
                <label for="set-weight" class="form-label">Set Weight</label>
                <input id="set-weight" name="set-weight" type="text" class="form-control" placeholder="" aria-label="Set Weight">
            </div>
            <div class="col">
                <label for="set-reps" class="form-label">Set Reps</label>
                <input id="set-reps" name="set-reps" type="text" class="form-control" placeholder="" aria-label="Set Reps">
            </div>
            <div class="col-12">
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </div>
        <button id="view-workout-modal-close">Close</button>
    </dialog>
    </div>
</main>
</body>
</html>