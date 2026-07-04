"use client";

import { useState } from "react";
import { AnalysisRequest } from "@/lib/types";

const projectTypes = [
  { value: "residential", label: "Residential" },
  { value: "commercial", label: "Commercial" },
  { value: "mixed_use", label: "Mixed Use" },
  { value: "office", label: "Office" },
  { value: "industrial", label: "Industrial" },
  { value: "hospitality", label: "Hospitality" },
];

interface AnalysisFormProps {
  lat: number | null;
  lng: number | null;
  onSubmit: (payload: AnalysisRequest) => Promise<void>;
}

export function AnalysisForm({ lat, lng, onSubmit }: AnalysisFormProps) {
  const [projectType, setProjectType] = useState("residential");
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);

    if (lat == null || lng == null) {
      setError("Please select a location on the map first.");
      return;
    }

    setLoading(true);
    try {
      await onSubmit({ lat, lng, projectType, description });
    } catch (err) {
      setError(err instanceof Error ? err.message : "Unexpected error");
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="dashboard-card h-full p-5">
      <div className="mb-6">
        <h2 className="text-xl font-semibold text-white">Project Input</h2>
        <p className="mt-1 text-sm text-slate-400">Select a site, define the project type, and run the urban feasibility analysis.</p>
      </div>

      <div className="space-y-4">
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Selected Coordinates</label>
          <div className="rounded-xl border border-white/10 bg-slate-950/40 px-4 py-3 text-sm text-slate-300">
            {lat != null && lng != null ? `${lat.toFixed(5)}, ${lng.toFixed(5)}` : "No location selected yet"}
          </div>
        </div>

        <div>
          <label htmlFor="projectType" className="mb-2 block text-sm font-medium text-slate-200">Project Type</label>
          <select
            id="projectType"
            value={projectType}
            onChange={(e) => setProjectType(e.target.value)}
            className="w-full rounded-xl border border-white/10 bg-slate-950/60 px-4 py-3 text-sm text-white outline-none ring-0"
          >
            {projectTypes.map((type) => (
              <option key={type.value} value={type.value}>
                {type.label}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label htmlFor="description" className="mb-2 block text-sm font-medium text-slate-200">Project Description</label>
          <textarea
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows={6}
            placeholder="Describe the development vision, expected use, density, or target market."
            className="w-full rounded-xl border border-white/10 bg-slate-950/60 px-4 py-3 text-sm text-white outline-none placeholder:text-slate-500"
          />
        </div>
      </div>

      {error && <div className="mt-4 rounded-xl border border-red-400/20 bg-red-500/10 px-4 py-3 text-sm text-red-200">{error}</div>}

      <button
        type="submit"
        disabled={loading}
        className="mt-6 w-full rounded-xl bg-brand-500 px-4 py-3 font-semibold text-white transition hover:bg-brand-400 disabled:cursor-not-allowed disabled:opacity-60"
      >
        {loading ? "Running Analysis..." : "Run Analysis"}
      </button>
    </form>
  );
}
