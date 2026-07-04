import { AnalysisRequest, AnalysisResponse } from "@/lib/types";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:8080/api";

export async function analyzeScenario(payload: AnalysisRequest): Promise<AnalysisResponse> {
  const response = await fetch(`${API_BASE_URL}/analyze`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(payload),
    cache: "no-store",
  });

  if (!response.ok) {
    throw new Error("Failed to analyze scenario");
  }

  return response.json();
}

export async function getScenario(id: string): Promise<AnalysisResponse> {
  const response = await fetch(`${API_BASE_URL}/scenario/${id}`, {
    cache: "no-store",
  });

  if (!response.ok) {
    throw new Error("Scenario not found");
  }

  return response.json();
}
