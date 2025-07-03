document.addEventListener("DOMContentLoaded", function() {
    fetch('http://localhost:8080/api/courses') // Adjust the URL if needed
        .then(response => response.json())
        .then(courses => {
            const courseList = document.getElementById('course-list');
            courses.forEach(course => {
                const courseCard = `
                    <div class="col-md-4">
                        <div class="card">
                            <img src="https://source.unsplash.com/300x200/?${course.title.toLowerCase()}" class="card-img-top" alt="${course.title}">
                            <div class="card-body">
                                <h5 class="card-title">${course.title}</h5>
                                <p class="card-text">${course.description}</p>
                                <a href="#" class="btn btn-primary">View Course</a>
                            </div>
                        </div>
                    </div>
                `;
                courseList.innerHTML += courseCard;
            });
        })
        .catch(error => console.error('Error fetching courses:', error));
});