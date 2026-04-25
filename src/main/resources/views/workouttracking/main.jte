@import java.util.UUID

@param UUID publicId
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

    <script src="https://code.jquery.com/jquery-4.0.0.min.js"
            integrity="sha256-OaVG6prZf4v69dPg6PhVattBXkcOWQB62pdZ3ORyrao=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.2.3/js/bootstrap.bundle.min.js"
            integrity="sha512-i9cEfJwUwViEPFKdC1enz4ZRGBj8YQo6QByFTF92YXHi7waCqyexvRD75S5NVTsSiTv7rKWqG9Y5eFxmRsOn0A=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.20/index.global.min.js'></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css' rel='stylesheet'>
    <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/bootstrap5@6.1.20/index.global.min.js'></script>
    <script src="https://cdn.jsdelivr.net/npm/lodash@4.18.1/lodash.min.js"></script>
    <script src="/js/calendar.js"></script>
    <script src="/js/jquery.serializejson.js"></script>
    <script>
        calendarInit("${publicId.toString()}");
    </script>
</head>
<body>
<main>
    <div class="d-flex justify-content-center">
        <div id="calendar"></div>
    </div>
</main>
<div class="modal fade" id="view-workout-modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <h2>Workout</h2>
            <div class="alert alert-success alert-dismissible fade show d-none" id="workout-added-success"
                 role="alert">
                <button type="button" id="close-workout-saved-success" class="btn-close"
                        aria-label="Close"></button>
            </div>
            <div class="alert alert-danger alert-dismissible fade show d-none" id="workout-added-failure"
                 role="alert">
                <button type="button" id="close-workout-saved-failed" class="btn-close"
                        aria-label="Close"></button>
            </div>
            <div id="view-workout-modal-day"></div>
            <div class="container">
                <form id="workout-data" class="row g-3">
                    <div id="workout-data-container" class="row">
                        <div id="workout-save-button-div" class="col-12">
                            <button type="submit" id="save-workout" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <button id="view-workout-modal-close">Close</button>
</div>
</body>
</html>