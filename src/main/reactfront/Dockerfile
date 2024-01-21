# 빌드 단계
# 베이스 이미지 선택
FROM node:17 AS build
# 작업 디텍토리 생성 및 설정
WORKDIR /app
# 애플리케이션 종속성 설치
COPY package*.json ./
RUN npm install
# 소스 코드 복사
COPY . .
# 애플리케이션 빌드
RUN npm run build

# 실행 단계
# 베이스 이미지 선택
FROM nginx:stable-alpine AS runtime
# Nginx 설정 파일 복사
#COPY nginx.conf /etc/nginx/conf.d/default.conf
# React 애플리케이션 빌드 결과물 복사
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

# 컨테이너 실행 시 Nginx 서버 시작
CMD ["nginx", "-g", "daemon off;"]
