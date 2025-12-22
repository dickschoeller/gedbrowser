import { looseInvalid } from './loose-invalid';

function formatValue(value: any): string {
    if (value instanceof Date) {
        return value.toISOString();
    }

    if (typeof value === 'object') {
        try {
            return JSON.stringify(value);
        } catch (e) {
            return String(value);
        }
    }

    return String(value);
}

export type ParamsObject = { [key: string]: string | string[] };

export function serialize(obj: any): ParamsObject {
    const params: ParamsObject = {};

    if (!obj) {
        return params;
    }

    // Use Object.keys to avoid iterating prototype properties
    for (const key of Object.keys(obj)) {
        const value = obj[key];
        if (looseInvalid(value)) {
            continue;
        }

        if (Array.isArray(value)) {
            const list: string[] = [];
            for (const v of value) {
                if (looseInvalid(v)) {
                    continue;
                }
                list.push(formatValue(v));
            }
            if (list.length > 0) {
                params[key] = list;
            }
        } else {
            params[key] = formatValue(value);
        }
    }

    return params;
}
