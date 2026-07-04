'use client';

import React from 'react';
import { MapContainer, TileLayer, Marker, useMapEvents } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

// تنظیم آیکون مارکر
const markerIcon = L.icon({
  iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
  iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
  shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
});

// این کامپوننت داخلی برای مدیریت کلیک روی نقشه است
function MapClickHandler({ onSelect }: { onSelect: (lat: number, lng: number) => void }) {
  useMapEvents({
    click(e) {
      onSelect(e.latlng.lat, e.latlng.lng);
    },
  });
  return null;
}

interface LocationMapProps {
  onSelect: (lat: number, lng: number) => void;
  selectedLat: number | null;
  selectedLng: number | null;
}

export default function LocationMap({ onSelect, selectedLat, selectedLng }: LocationMapProps) {
  const defaultCenter: [number, number] = [35.69276, 51.40807];

  return (
    <div className="h-[600px] w-full overflow-hidden rounded-2xl border border-white/10 shadow-2xl">
     <MapContainer
       center={defaultCenter}
       zoom={13}
       scrollWheelZoom={true}
       style={{ height: '100%', width: '100%', backgroundColor: '#2d3748' }} // رنگ پس‌زمینه موقت برای تست
       className="h-full w-full"
     >

        <TileLayer
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        {selectedLat !== null && selectedLng !== null && (
          <Marker position={[selectedLat, selectedLng]} icon={markerIcon} />
        )}

        {/* مدیریت کلیک کاربر */}
        <MapClickHandler onSelect={onSelect} />
      </MapContainer>
    </div>
  );
}
