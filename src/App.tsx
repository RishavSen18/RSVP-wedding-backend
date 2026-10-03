import React, { useState } from 'react';
import { 
  Server, 
  Database, 
  Terminal, 
  CheckCircle2, 
  Code2, 
  FileText, 
  Layers, 
  ShieldCheck, 
  ExternalLink,
  Copy,
  Check
} from 'lucide-react';

export default function App() {
  const [copiedKey, setCopiedKey] = useState<string | null>(null);

  const copyToClipboard = (text: string, key: string) => {
    navigator.clipboard.writeText(text);
    setCopiedKey(key);
    setTimeout(() => setCopiedKey(null), 2000);
  };

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 font-sans antialiased">
      {/* Top Header */}
      <header className="border-b border-slate-800 bg-slate-900/60 backdrop-blur-md sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 rounded-lg bg-emerald-500/10 border border-emerald-500/30 flex items-center justify-center text-emerald-400">
              <Server className="w-5 h-5" />
            </div>
            <div>
              <div className="flex items-center space-x-2">
                <span className="font-bold text-slate-100 tracking-tight text-lg">Wedding RSVP REST API</span>
                <span className="px-2 py-0.5 text-xs font-medium rounded-full bg-emerald-500/10 text-emerald-400 border border-emerald-500/30">
                  Spring Boot 3.3
                </span>
                <span className="px-2 py-0.5 text-xs font-medium rounded-full bg-blue-500/10 text-blue-400 border border-blue-500/30">
                  Java 17
                </span>
              </div>
              <p className="text-xs text-slate-400">Backend service for external React wedding invitation application</p>
            </div>
          </div>

          <div className="flex items-center space-x-3">
            <span className="inline-flex items-center px-3 py-1 rounded-full text-xs font-medium bg-emerald-950 text-emerald-300 border border-emerald-800/80">
              <span className="w-2 h-2 mr-2 rounded-full bg-emerald-400 animate-pulse"></span>
              Backend Ready
            </span>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-8">
        
        {/* Notice Banner: Backend Only Architecture */}
        <div className="bg-slate-900/80 border border-indigo-500/30 rounded-xl p-5 shadow-lg relative overflow-hidden">
          <div className="flex items-start space-x-4">
            <div className="p-2.5 rounded-lg bg-indigo-500/10 text-indigo-400 border border-indigo-500/20 shrink-0">
              <Layers className="w-6 h-6" />
            </div>
            <div className="space-y-1">
              <h2 className="text-base font-semibold text-slate-100">Standalone Spring Boot Architecture</h2>
              <p className="text-sm text-slate-300 leading-relaxed">
                This project is the dedicated <strong>Spring Boot REST API</strong> backend designed to receive RSVP submissions from your external React wedding website and serve response data to your separate <code className="text-indigo-300 bg-slate-800 px-1.5 py-0.5 rounded text-xs">/wedding-response</code> admin viewer. Per project specifications, no frontend wedding UI or sample data is baked into this service.
              </p>
            </div>
          </div>
        </div>

        {/* System Capabilities Grid */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-slate-900/50 border border-slate-800 rounded-xl p-5 hover:border-slate-700 transition">
            <div className="flex items-center space-x-3 mb-3 text-emerald-400">
              <Database className="w-5 h-5" />
              <h3 className="font-semibold text-slate-200">PostgreSQL + Flyway</h3>
            </div>
            <p className="text-xs text-slate-400 leading-relaxed mb-3">
              Automated schema migration with Flyway (<code className="text-slate-300">V1__create_rsvp_responses.sql</code>). Strict data integrity with timestamp indexing.
            </p>
            <div className="text-xs text-slate-500 font-mono">Table: rsvp_responses</div>
          </div>

          <div className="bg-slate-900/50 border border-slate-800 rounded-xl p-5 hover:border-slate-700 transition">
            <div className="flex items-center space-x-3 mb-3 text-cyan-400">
              <ShieldCheck className="w-5 h-5" />
              <h3 className="font-semibold text-slate-200">Zero Authentication</h3>
            </div>
            <p className="text-xs text-slate-400 leading-relaxed mb-3">
              Unauthenticated public endpoints for seamless wedding guest RSVP submission and frictionless coordinator review, fully decoupled for future auth hooks.
            </p>
            <div className="text-xs text-slate-500 font-mono">No JWT / No Passwords</div>
          </div>

          <div className="bg-slate-900/50 border border-slate-800 rounded-xl p-5 hover:border-slate-700 transition">
            <div className="flex items-center space-x-3 mb-3 text-amber-400">
              <Code2 className="w-5 h-5" />
              <h3 className="font-semibold text-slate-200">Jakarta Validation</h3>
            </div>
            <p className="text-xs text-slate-400 leading-relaxed mb-3">
              Strict Bean Validation on fields and AttendanceStatus enum (<code className="text-slate-300">JOYFULLY_ACCEPT</code>, <code className="text-slate-300">REGRETFULLY_DECLINE</code>).
            </p>
            <div className="text-xs text-slate-500 font-mono">Standard JSON Errors</div>
          </div>
        </div>

        {/* REST API Endpoints Reference */}
        <div className="bg-slate-900/40 border border-slate-800 rounded-xl overflow-hidden">
          <div className="p-5 border-b border-slate-800 flex items-center justify-between">
            <div>
              <h2 className="text-lg font-semibold text-slate-100 flex items-center gap-2">
                <Terminal className="w-5 h-5 text-indigo-400" />
                REST API Specification
              </h2>
              <p className="text-xs text-slate-400 mt-1">Configured for CORS with your React frontend origin</p>
            </div>
            <span className="text-xs text-slate-400 font-mono">Base: /api</span>
          </div>

          <div className="divide-y divide-slate-800/60">
            {/* Health Check */}
            <div className="p-5 flex flex-col lg:flex-row lg:items-center justify-between gap-4 hover:bg-slate-900/30 transition">
              <div className="space-y-1">
                <div className="flex items-center space-x-3">
                  <span className="px-2.5 py-1 text-xs font-bold rounded bg-sky-950 text-sky-400 border border-sky-800 font-mono">
                    GET
                  </span>
                  <span className="font-mono text-sm text-slate-200 font-semibold">/api/health</span>
                </div>
                <p className="text-xs text-slate-400">Application health check and uptime probe.</p>
              </div>
              <div className="bg-slate-950 px-3 py-1.5 rounded font-mono text-xs text-slate-300 border border-slate-800">
                {`{ "status": "UP" }`}
              </div>
            </div>

            {/* Submit RSVP */}
            <div className="p-5 flex flex-col lg:flex-row lg:items-start justify-between gap-4 hover:bg-slate-900/30 transition">
              <div className="space-y-2 max-w-xl">
                <div className="flex items-center space-x-3">
                  <span className="px-2.5 py-1 text-xs font-bold rounded bg-emerald-950 text-emerald-400 border border-emerald-800 font-mono">
                    POST
                  </span>
                  <span className="font-mono text-sm text-slate-200 font-semibold">/api/rsvp</span>
                  <span className="text-xs bg-slate-800 text-slate-300 px-2 py-0.5 rounded">Guest Form Submission</span>
                </div>
                <p className="text-xs text-slate-400">
                  Accepts RSVP submission from React form. Automatically generates <code className="text-emerald-300">submittedAt</code> timestamp. Returns 201 Created without leaking private guest record.
                </p>
                <div className="text-xs text-slate-400 flex flex-wrap gap-2 pt-1">
                  <span className="bg-slate-800/80 px-2 py-0.5 rounded border border-slate-700/60">firstName (required)</span>
                  <span className="bg-slate-800/80 px-2 py-0.5 rounded border border-slate-700/60">lastName (required)</span>
                  <span className="bg-slate-800/80 px-2 py-0.5 rounded border border-slate-700/60">phoneNumber (required String)</span>
                  <span className="bg-slate-800/80 px-2 py-0.5 rounded border border-slate-700/60">attendance (Enum)</span>
                  <span className="bg-slate-800/80 px-2 py-0.5 rounded border border-slate-700/60">message (optional)</span>
                </div>
              </div>
              <div className="bg-slate-950 p-3 rounded font-mono text-xs text-slate-300 border border-slate-800 min-w-[280px]">
                <div className="text-slate-500 mb-1">// 201 Created Response</div>
                {`{\n  "success": true,\n  "message": "RSVP submitted successfully"\n}`}
              </div>
            </div>

            {/* List RSVPs */}
            <div className="p-5 flex flex-col lg:flex-row lg:items-start justify-between gap-4 hover:bg-slate-900/30 transition">
              <div className="space-y-2 max-w-xl">
                <div className="flex items-center space-x-3">
                  <span className="px-2.5 py-1 text-xs font-bold rounded bg-sky-950 text-sky-400 border border-sky-800 font-mono">
                    GET
                  </span>
                  <span className="font-mono text-sm text-slate-200 font-semibold">/api/rsvp</span>
                  <span className="text-xs bg-slate-800 text-slate-300 px-2 py-0.5 rounded">/wedding-response Dashboard</span>
                </div>
                <p className="text-xs text-slate-400">
                  Returns all stored RSVP responses sorted newest first (<code className="text-sky-300">submittedAt DESC</code>). Used by your React dashboard to calculate totals, accepted, and declined counts.
                </p>
              </div>
              <div className="bg-slate-950 p-3 rounded font-mono text-xs text-slate-300 border border-slate-800 min-w-[280px]">
                <div className="text-slate-500 mb-1">// Array of RsvpResponseDto</div>
                {`[\n  {\n    "id": 1,\n    "firstName": "Subhashish",\n    "lastName": "Mukherjee",\n    "phoneNumber": "+919830000000",\n    "attendance": "JOYFULLY_ACCEPT",\n    "submittedAt": "2026-10-02T14:40:00"\n  }\n]`}
              </div>
            </div>

            {/* Get Single RSVP */}
            <div className="p-5 flex flex-col lg:flex-row lg:items-center justify-between gap-4 hover:bg-slate-900/30 transition">
              <div className="space-y-1">
                <div className="flex items-center space-x-3">
                  <span className="px-2.5 py-1 text-xs font-bold rounded bg-sky-950 text-sky-400 border border-sky-800 font-mono">
                    GET
                  </span>
                  <span className="font-mono text-sm text-slate-200 font-semibold">/api/rsvp/&#123;id&#125;</span>
                </div>
                <p className="text-xs text-slate-400">Retrieve a single RSVP record by ID. Returns 404 if not found.</p>
              </div>
              <div className="bg-slate-950 px-3 py-1.5 rounded font-mono text-xs text-slate-300 border border-slate-800">
                Returns RsvpResponseDto or 404
              </div>
            </div>

            {/* Delete RSVP */}
            <div className="p-5 flex flex-col lg:flex-row lg:items-center justify-between gap-4 hover:bg-slate-900/30 transition">
              <div className="space-y-1">
                <div className="flex items-center space-x-3">
                  <span className="px-2.5 py-1 text-xs font-bold rounded bg-rose-950 text-rose-400 border border-rose-800 font-mono">
                    DELETE
                  </span>
                  <span className="font-mono text-sm text-slate-200 font-semibold">/api/rsvp/&#123;id&#125;</span>
                </div>
                <p className="text-xs text-slate-400">Delete an RSVP response by ID from the /wedding-response dashboard.</p>
              </div>
              <div className="bg-slate-950 px-3 py-1.5 rounded font-mono text-xs text-slate-300 border border-slate-800">
                {`{ "success": true, "message": "RSVP response deleted successfully" }`}
              </div>
            </div>
          </div>
        </div>

        {/* Bengali Form Mapping & PostgreSQL Schema */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Bengali Mapping Card */}
          <div className="bg-slate-900/40 border border-slate-800 rounded-xl p-5">
            <h3 className="text-sm font-semibold text-slate-200 mb-3 flex items-center gap-2">
              <FileText className="w-4 h-4 text-emerald-400" />
              React Form Field Mapping
            </h3>
            <div className="space-y-3 text-xs">
              <div className="p-3 bg-slate-950 rounded-lg border border-slate-800/80">
                <div className="text-slate-400 font-medium">FIRST NAME · প্রথম নাম</div>
                <div className="font-mono text-emerald-300 mt-1">String firstName (Required, non-blank)</div>
              </div>

              <div className="p-3 bg-slate-950 rounded-lg border border-slate-800/80">
                <div className="text-slate-400 font-medium">LAST NAME · পদবি / শেষ নাম</div>
                <div className="font-mono text-emerald-300 mt-1">String lastName (Required, non-blank)</div>
              </div>

              <div className="p-3 bg-slate-950 rounded-lg border border-slate-800/80">
                <div className="text-slate-400 font-medium">PHONE NUMBER · ফোন নম্বর</div>
                <div className="font-mono text-emerald-300 mt-1">String phoneNumber (Supports +91, leading zeroes)</div>
              </div>

              <div className="p-3 bg-slate-950 rounded-lg border border-slate-800/80">
                <div className="text-slate-400 font-medium">WILL YOU ATTEND? · আপনার উপস্থিতি</div>
                <div className="font-mono text-emerald-300 mt-1">
                  JOYFULLY_ACCEPT (আনন্দসহকারে উপস্থিত থাকব)<br />
                  REGRETFULLY_DECLINE (সম্ভবত অপারগতা)
                </div>
              </div>

              <div className="p-3 bg-slate-950 rounded-lg border border-slate-800/80">
                <div className="text-slate-400 font-medium">BLESSINGS & MESSAGE · যুগলের উদ্দেশ্য আশীর্বাদ ও বার্তা</div>
                <div className="font-mono text-emerald-300 mt-1">String message (Optional, max 1000 chars)</div>
              </div>

              <div className="p-3 bg-slate-950 rounded-lg border border-slate-800/80">
                <div className="text-slate-400 font-medium">Strict Compliance Guarantee</div>
                <div className="font-mono text-rose-300 mt-1">NO email, NO guest_count, NO number_of_guests</div>
              </div>
            </div>
          </div>

          {/* Quick React Integration Snippet */}
          <div className="bg-slate-900/40 border border-slate-800 rounded-xl p-5 flex flex-col justify-between">
            <div>
              <div className="flex items-center justify-between mb-3">
                <h3 className="text-sm font-semibold text-slate-200 flex items-center gap-2">
                  <Code2 className="w-4 h-4 text-indigo-400" />
                  React Form Submission Handler
                </h3>
                <button
                  onClick={() => copyToClipboard(`const handleSubmit = async (e) => {
  e.preventDefault();
  const res = await fetch(\`\${API_URL}/api/rsvp\`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      firstName: form.firstName,
      lastName: form.lastName,
      phoneNumber: form.phoneNumber,
      attendance: form.willAttend ? 'JOYFULLY_ACCEPT' : 'REGRETFULLY_DECLINE',
      message: form.message || null
    })
  });
  const data = await res.json();
  if (data.success) {
    triggerCelebrationAnimation();
  }
};`, 'react-snippet')}
                  className="text-xs text-slate-400 hover:text-slate-200 flex items-center gap-1 bg-slate-800 px-2 py-1 rounded"
                >
                  {copiedKey === 'react-snippet' ? <Check className="w-3.5 h-3.5 text-emerald-400" /> : <Copy className="w-3.5 h-3.5" />}
                  <span>{copiedKey === 'react-snippet' ? 'Copied' : 'Copy'}</span>
                </button>
              </div>

              <pre className="p-3.5 bg-slate-950 rounded-lg border border-slate-800 text-xs font-mono text-slate-300 overflow-x-auto leading-relaxed">
{`const handleSubmit = async (e) => {
  e.preventDefault();
  const res = await fetch(\`\${API_URL}/api/rsvp\`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      firstName: form.firstName,
      lastName: form.lastName,
      phoneNumber: form.phoneNumber,
      attendance: form.willAttend 
        ? 'JOYFULLY_ACCEPT' 
        : 'REGRETFULLY_DECLINE',
      message: form.message || null
    })
  });
  const data = await res.json();
  if (data.success) {
    triggerCelebrationAnimation();
  }
};`}
              </pre>
            </div>

            <div className="mt-4 pt-3 border-t border-slate-800 text-xs text-slate-400 flex items-center justify-between">
              <span>Run backend: <code className="text-emerald-400 bg-slate-950 px-1 py-0.5 rounded font-mono">mvn spring-boot:run</code></span>
              <span>Run tests: <code className="text-emerald-400 bg-slate-950 px-1 py-0.5 rounded font-mono">mvn test</code></span>
            </div>
          </div>
        </div>

      </main>
    </div>
  );
}
