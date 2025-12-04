FROM nginx:1.27-alpine

WORKDIR /usr/share/nginx/html

# Remove default nginx content
RUN rm -rf ./*

# Copy your static files (index.html, css, js, etc.)
COPY . .

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
