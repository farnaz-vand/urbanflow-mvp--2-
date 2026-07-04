"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import LocationMap from "@/components/LocationMap";
import { AnalysisForm } from "@/components/AnalysisForm";
import { analyzeScenario } from "@/lib/api";
import { AnalysisRequest } from "@/lib/types";
import Link from "next/link";

export default function MapPage() {
  const router = useRouter();
  const [selectedLat, setSelectedLat] = useState<number | null>(null);
  const [selectedLng, setSelectedLng] = useState<number | null>(null);

  const handleSelect = (lat: number, lng: number) => {
    setSelectedLat(lat);
    setSelectedLng(lng);
  };

  const handleSubmit = async (payload: AnalysisRequest) => {
    const result = await analyzeScenario(payload);
    router.push(`/result/${result.id}`);
  };

  return (
    <main className="min-h-screen px-6 py-8">
      <div className="mx-auto max-w-7xl">
        <header className="mb-6 flex flex-col gap-4 rounded-2xl border border-white/10 bg-white/5 px-6 py-5 backdrop-blur-md md:flex-row md:items-center md:justify-between">
          <div>
            <div className="text-sm uppercase tracking-[0.22em] text-brand-200">UrbanFlow / Analysis Workspace</div>
            <h1 className="mt-2 text-3xl font-black text-white">Map-Centric Development Analysis</h1>
            <p className="mt-2 text-sm text-slate-400">Click anywhere on the map to select a site, then define a project and run the AI assessment.</p>
          </div>
          <Link href="/" className="rounded-xl border border-white/10 bg-white/5 px-4 py-3 text-sm font-medium text-slate-200 transition hover:bg-white/10">
            ← Back to Landing
          </Link>
        </header>

        <section className="grid gap-6 xl:grid-cols-[1.45fr_0.75fr]">
          <div>
            <LocationMap onSelect={handleSelect} selectedLat={selectedLat} selectedLng={selectedLng} />
          </div>
          <div>
            <AnalysisForm lat={selectedLat} lng={selectedLng} onSubmit={handleSubmit} />
          </div>
        </section>
      </div>
    </main>
  );
}
