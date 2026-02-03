const apiUrl = "http://localhost:8080/api/"

export const ENDPOINTS = {
  LOGIN: apiUrl + "auth/login",
  CHANGE_PASSWORD: apiUrl + "auth/changepassword",
  ME: apiUrl + "auth/me",

  USERS: apiUrl + "users",
  USER: (id: string) => `${apiUrl}users/${id}`,
  CREATE_USERS: apiUrl + "users/create",
  DELETE_USER: (id: string) => `${apiUrl}users/${id}/delete`,

  ENVIRONMENTS: apiUrl + "environments",
  ADD_ENVIRONMENT: apiUrl + "environments/create",
  DELETE_ENVIRONMENT: (id: string) => `${apiUrl}environments/${id}/delete`,

  FEATURE_BY_ID: (id: string) => `${apiUrl}features/${id}/data`,
  FEATURES_ENVIRONMENT: (environment: string) => `${apiUrl}features/${environment}`,
  FEATURE_UPDATE: (id: string) => `${apiUrl}features/${id}/toggle`,
  CREATE_FEATURE: apiUrl + "features/create",
  DELETE_FEATURE: (id: string) => `${apiUrl}features/${id}/delete`,
}
