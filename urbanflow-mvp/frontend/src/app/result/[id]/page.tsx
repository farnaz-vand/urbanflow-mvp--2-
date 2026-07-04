import Link from "next/link";
import { getScenario } from "@/lib/api";
import { ScoreBadge } from "@/components/ScoreBadge";
import { InsightList } from "@/components/InsightList";
import ScenarioPreviewMap from "@/components/ScenarioPreviewMap";

export default async function ResultPage({ params }: { params: Promise<{ id: string }> }) {
  const { id } = await params;
  const scenario = await getScenario(id);

  return (
    <main className="min-h-screen px-6 py-8">
      <div className="mx-auto max-w-7xl">
        <header className="mb-6 flex flex-col gap-4 rounded-2xl border border-white/10 bg-white/5 px-6 py-5 backdrop-blur-md md:flex-row md:items-center md:justify-between">
          <div>
            <div className="text-sm uppercase tracking-[0.22em] text-brand-200">Analysis Result #{scenario.id}</div>
            <h1 className="mt-2 text-3xl font-black text-white">Development Feasibility Output</h1>
            <p className="mt-2 text-sm text-slate-400">A startup-grade, map-first urban intelligence summary for the selected site.</p>
          </div>
          <div className="flex gap-3">
            <Link href="/map" className="rounded-xl bg-brand-500 px-4 py-3 text-sm font-semibold text-white transition hover:bg-brand-400">
              New Analysis
            </Link>
            <Link href="/" className="rounded-xl border border-white/10 bg-white/5 px-4 py-3 text-sm font-medium text-slate-200 transition hover:bg-white/10">
              Home
            </Link>
          </div>
        </header>

        <section className="grid gap-6 lg:grid-cols-[0.9fr_1.1fr]">
          <div className="space-y-6">
            <ScoreBadge score={scenario.score} risk={scenario.risk} />

            <div className="dashboard-card p-5">
              <h3 className="text-lg font-semibold text-white">Recommendation</h3>
              <p className="mt-3 rounded-xl border border-brand-400/15 bg-brand-500/10 p-4 text-sm leading-7 text-slate-200">
                {scenario.recommendation}
              </p>
            </div>

            <div className="dashboard-card p-5">
              <h3 className="text-lg font-semibold text-white">Scenario Metadata</h3>
              <div className="mt-4 grid gap-3 text-sm text-slate-300 sm:grid-cols-2">
                <div className="rounded-xl border border-white/10 bg-white/5 p-3">
                  <div className="text-xs uppercase tracking-wide text-slate-500">Project Type</div>
                  <div className="mt-1 font-medium text-white">{scenario.projectType.replace("_", " ")}</div>
                </div>
                <div className="rounded-xl border border-white/10 bg-white/5 p-3">
                  <div className="text-xs uppercase tracking-wide text-slate-500">Coordinates</div>
                  <div className="mt-1 font-medium text-white">{scenario.lat.toFixed(5)}, {scenario.lng.toFixed(5)}</div>
                </div>
                <div className="rounded-xl border border-white/10 bg-white/5 p-3 sm:col-span-2">
                  <div className="text-xs uppercase tracking-wide text-slate-500">Description</div>
                  <div className="mt-1 leading-6 text-slate-200">{scenario.description || "No additional description provided."}</div>
                </div>
              </div>
            </div>

            <InsightList insights={scenario.insights} />
          </div>

          <div>
            <ScenarioPreviewMap
              lat={scenario.lat}
              lng={scenario.lng}
              score={scenario.score}
              risk={scenario.risk}
              projectType={scenario.projectType}
            />
          </div>
        </section>
      </div>
    </main>
  );
}
