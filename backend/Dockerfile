# Use an official MinIO image as a base
FROM quay.io/minio/minio

# Set environment variables for MinIO access and secret keys
ENV MINIO_ACCESS_KEY=youraccesskey
ENV MINIO_SECRET_KEY=yoursecretkey

# Set the default command to run MinIO server
CMD ["server", "/data"]

# Expose the necessary ports
EXPOSE 9000
EXPOSE 9001