# Train Booking App

A responsive Java Spring Boot app for booking train tickets, with session-based auth and external image URLs in HTML. Practices DevOps concepts.

## Features
- User registration/login with session persistence.
- Search trains by route/date.
- Book/cancel tickets.
- Responsive UI with Bootstrap and external images (no local files needed).

## Setup
- Clone: `git clone https://github.com/yourusername/train-booking-app.git`
- Build WAR: `mvn clean package`
- Run embedded: `mvn spring-boot:run` (http://localhost:8080)
- Deploy to Tomcat: `mvn tomcat7:deploy` (configure credentials in pom/settings.xml)
- Test: `mvn test`

## Docker
- Build: `docker build -f docker/Dockerfile-tomcat -t train-tomcat-app .`
- Run: `docker run -p 8080:8080 train-tomcat-app` (http://localhost:8080/train-booking-app)

## DevOps
- CI/CD: GitHub Actions builds WAR/image.
- Kubernetes: `kubectl apply -f k8s/`

## Images
Images are loaded from external URLs (e.g., icons8.com, Unsplash) directly in HTML—no local folder needed. Replace URLs for customization.
