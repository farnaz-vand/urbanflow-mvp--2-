export type RiskLevel = "low" | "medium" | "high";

export interface AnalysisRequest {
  lat: number;
  lng: number;
  projectType: string;
  description: string;
}

export interface AnalysisResponse {
  id: number;
  score: number;
  risk: RiskLevel;
  insights: string[];
  recommendation: string;
  lat: number;
  lng: number;
  projectType: string;
  description: string;
  createdAt: string;
}
