import Link from "next/link";

export default function HomePage() {
  return (
    <main className="relative min-h-screen overflow-hidden px-6 py-10">
      <div className="grid-bg absolute inset-0 bg-grid opacity-20" />

      <div className="relative mx-auto flex min-h-[90vh] max-w-7xl flex-col justify-between">
        <header className="flex items-center justify-between rounded-2xl border border-white/10 bg-white/5 px-6 py-4 backdrop-blur-md">
          <div>
            <div className="text-lg font-bold text-white">UrbanFlow</div>
            <div className="text-xs uppercase tracking-[0.24em] text-slate-400">LandIQ MVP</div>
          </div>
          <div className="rounded-full border border-brand-400/20 bg-brand-500/10 px-3 py-1 text-xs font-semibold uppercase tracking-wide text-brand-200">
            AI-Powered Urban Decisions
          </div>
        </header>

        <section className="grid items-center gap-12 py-16 lg:grid-cols-[1.2fr_0.8fr]">
          <div>
            <div className="mb-4 inline-flex rounded-full border border-brand-400/20 bg-brand-500/10 px-4 py-2 text-xs font-semibold uppercase tracking-[0.2em] text-brand-200">
              Urban investment analysis, simplified
            </div>
            <h1 className="max-w-4xl text-5xl font-black leading-tight text-white sm:text-6xl">
              Evaluate land and development potential with an AI-first urban feasibility MVP.
            </h1>
            <p className="mt-6 max-w-2xl text-lg leading-8 text-slate-300">
              Pick a location on the map, define the project type, and instantly get a development score, risk assessment, strategic insights, and a simple before/after scenario view.
            </p>
            <div className="mt-10 flex flex-wrap items-center gap-4">
              <Link
                href="/map"
                className="rounded-2xl bg-brand-500 px-6 py-4 text-base font-semibold text-white transition hover:bg-brand-400"
              >
                Start Analysis
              </Link>
              <div className="rounded-2xl border border-white/10 bg-white/5 px-5 py-4 text-sm text-slate-300 backdrop-blur-md">
                MVP Scope: no 3D, no BIM, no digital twin complexity — just a working decision engine.
              </div>
            </div>
          </div>

          <div className="dashboard-card p-6">
            <div className="mb-6 flex items-center justify-between">
              <div>
                <h2 className="text-2xl font-semibold text-white">Demo Journey</h2>
                <p className="mt-1 text-sm text-slate-400">Built for fast validation and investor-grade storytelling.</p>
              </div>
              <div className="text-4xl">🗺️</div>
            </div>

            <div className="space-y-3">
              {[
                "Open the app",
                "Click Start Analysis",
                "Select a site on the map",
                "Choose project type",
                "Run AI analysis",
                "Review score, insights, and before/after view",
              ].map((step, index) => (
                <div key={index} className="flex items-center gap-4 rounded-xl border border-white/10 bg-white/5 p-4">
                  <div className="flex h-10 w-10 items-center justify-center rounded-full bg-brand-500/20 font-bold text-brand-100">
                    {index + 1}
                  </div>
                  <div className="text-sm text-slate-200">{step}</div>
                </div>
              ))}
            </div>
          </div>
        </section>
      </div>
    </main>
  );
}
