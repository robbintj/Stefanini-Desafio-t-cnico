# Dockerfile para Frontend Angular

# Stage 1: Build
FROM node:18-alpine AS build
WORKDIR /app

# Copiar package files
COPY package*.json ./

# Instalar dependências
RUN npm ci

# Copiar código fonte
COPY . .

# Build da aplicação
RUN npm run build

# Stage 2: Runtime com Nginx
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Remover arquivos padrão do nginx
RUN rm -rf ./*

# Copiar build do Angular
COPY --from=build /app/dist/frontend/browser ./

# Copiar configuração customizada do nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expor porta
EXPOSE 80

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost || exit 1

# Iniciar nginx
CMD ["nginx", "-g", "daemon off;"]

