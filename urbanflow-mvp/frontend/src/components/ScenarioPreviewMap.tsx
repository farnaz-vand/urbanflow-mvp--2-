"use client";

import { useRef } from "react";

interface ScenarioPreviewMapProps {
  latitude?: number;
  longitude?: number;
  lat?: number;
  lng?: number;
  title?: string;
  className?: string;
  score: number;
}

export default function ScenarioPreviewMap({
  latitude,
  longitude,
  lat,
  lng,
  title = "Scenario Location",
  className = "",
  score,
}: ScenarioPreviewMapProps) {
  const mapLat = latitude ?? lat ?? 35.6892;
  const mapLng = longitude ?? lng ?? 51.389;

  const delta = 0.005;
  const bbox = `${mapLng - delta}%2C${mapLat - delta}%2C${mapLng + delta}%2C${mapLat + delta}`;

  const osmUrl = `https://www.openstreetmap.org/export/embed.html?bbox=${bbox}&layer=mapnik&marker=${mapLat}%2C${mapLng}`;

  return (
    <div
      className={`relative flex flex-col overflow-hidden rounded-2xl border border-slate-200 bg-slate-50 ${className}`}
    >
      <div className="flex items-center justify-between border-b border-slate-200 bg-white/80 px-4 py-3 backdrop-blur-sm">
        <div>
          <h3 className="text-sm font-bold text-slate-900">{title}</h3>
          <p className="text-[10px] font-mono text-slate-500 uppercase tracking-wider">
            GPS: {mapLat.toFixed(4)}, {mapLng.toFixed(4)}
          </p>
        </div>
        <div className="flex h-2 w-2 animate-pulse rounded-full bg-green-500"></div>
      </div>

      <div className="relative h-72 w-full bg-slate-100">
        <iframe
          title="UrbanFlow Location Preview"
          src={osmUrl}
          className="h-full w-full border-0 grayscale-[0.2] contrast-[1.1]"
          loading="lazy"
        />

        <div className="pointer-events-none absolute inset-0 shadow-[inset_0_0_40px_rgba(0,0,0,0.05)]"></div>
      </div>
      <p className="text-[10px] font-mono text-slate-500 uppercase tracking-wider">
        Score: {score}
      </p>

      <div className="bg-white px-4 py-2 text-[10px] text-slate-400">
        Powered by OpenStreetMap (No Token Required)
      </div>
    </div>
  );
}
