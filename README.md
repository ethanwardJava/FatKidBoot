# FatKidBoot

A secure, topic-based chatroom platform built for students and self-learners.

FatKidBoot lets you join focused discussion lobbies (max 30 members per room) based on the subject you're studying. When a topic gets popular (e.g., 300 users learning Java), the system automatically creates multiple lobbies so everyone can participate without overcrowding).

Perfect for course groups, bootcamps, or solo learners who want real peers to discuss with.

## Features

- Topic-specific chat lobbies (Java, Python, React, Math, etc.)
- Automatic lobby scaling – 30 users per room, unlimited rooms per topic
- End-to-end encrypted messaging
- Personal dashboard with:
  - Built-in to-do list
  - Pomodoro/study timer
  - Course links section – share what you're learning (YouTube, Udemy, Coursera, freeCodeCamp, etc.)
- Real-time chat with markdown support)
- User profiles showing current courses and progress
- Clean, distraction-free UI

## Tech Stack

- Backend: Java 21 + Spring Boot
- Real-time: WebSocket (Spring WebSocket + STOMP)
- Database: PostgreSQL
- Authentication: JWT
- Deployment: Docker + Docker Compose


git clone https://github.com/AlirezaAkhavanJava/FatKidBoot.git
cd FatKidBoot
docker-compose up --build
