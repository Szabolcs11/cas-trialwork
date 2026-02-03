import {UserModel} from './UserModel';
import {FeatureModel} from './FeatureModel';
import {EnvironmentModel} from './EnvironmentModel';
import {FeatureEnvironment} from './FeatureEnvironmentModel';

export interface GetUsersResponse {
  id: string;
  name: string;
  protected: boolean;
}

export interface UserDataResponse {
  success: boolean;
  data: UserModel[];
}

export interface FeatureDataResponse {
  success: boolean;
  data: FeatureModel;
  environmentFeature: FeatureEnvironment[];
}

export interface FeaturesDataResponse {
  success: boolean;
  data: FeatureModel[];
}

export interface EnvironmentModelResponse {
  success: boolean;
  data: EnvironmentModel[];
}

export interface MessageResponse {
  success: boolean;
  message: string;
}

export interface LoginResponse {
  success: boolean;
  message?: string;
  data: {
    token: string;
    lastLogin: string;
  }
}

export interface AuthenticateResponse {
  success: true,
  data: UserModel;
}
