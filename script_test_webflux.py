import asyncio
import aiohttp
import time
import sys

# --- PARÁMETROS DE CARGA ---
URL = "http://localhost:8632/api/async/procesar_datos"
REQUESTS_TOTAL = 500 # Número total de peticiones
TIMEOUT = None              # Sin timeout individual

# ---------------------------

async def make_request(session, idx, stats):
    """Función que realiza una petición POST individual sin semáforo y sin timeout."""
    start = time.perf_counter()
    req_json = {"nombre": f"Prueba-{idx}", "datosNumericos": idx}
    try:
        async with session.post(URL, json=req_json) as resp:
            await resp.text()  # leer respuesta
            elapsed = time.perf_counter() - start
            if 200 <= resp.status < 300:
                stats['success'] += 1
                stats['times'].append(elapsed)
            else:
                stats['fail'] += 1
                stats['errors'].append(f"HTTP Status {resp.status}")
    except Exception as ex:
        stats['fail'] += 1
        stats['errors'].append(str(ex))

async def test_concurrency():
    stats = {'success': 0, 'fail': 0, 'times': [], 'errors': []}
    conn = aiohttp.TCPConnector(limit=None)  # permitir muchas conexiones simultáneas
    async with aiohttp.ClientSession(connector=conn) as session:
        # Crear todas las tareas sin semáforo
        tasks = [make_request(session, i, stats) for i in range(REQUESTS_TOTAL)]
        print(f"Lanzando {REQUESTS_TOTAL} peticiones simultáneamente...")
        await asyncio.gather(*tasks)
    return stats

def print_summary(stats):
    total_requests = stats['success'] + stats['fail']
    mean_time = max_time = 0
    if stats['times']:
        mean_time = sum(stats['times']) / len(stats['times'])
        max_time = max(stats['times'])
    print(f"\n--- Resultado de Carga Masiva ---")
    print(f"Peticiones Totales: {total_requests}")
    print(f"Éxito: {stats['success']}, Fallos: {stats['fail']}")
    print(f"Tiempo de respuesta Promedio: {mean_time:.3f} s")
    print(f"Tiempo de respuesta Máximo: {max_time:.3f} s")
    if stats['fail'] > 0:
        print(f"Tasa de Fallos: {(stats['fail'] / total_requests) * 100:.2f}%")
        print("Errores únicos encontrados:", {e for e in stats['errors']})
    print("-----------------------------------------------------")

async def main():
    print(f"Iniciando prueba de carga de {REQUESTS_TOTAL} peticiones en {URL}...")
    stats = await test_concurrency()
    print_summary(stats)

if __name__ == "__main__":
    if sys.platform == "win32":
        asyncio.set_event_loop_policy(asyncio.WindowsSelectorEventLoopPolicy())
    asyncio.run(main())
