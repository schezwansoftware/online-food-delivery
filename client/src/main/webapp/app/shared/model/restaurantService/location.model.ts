export interface ILocation {
    id?: string;
    address?: string;
    state?: string;
    city?: string;
    locality?: string;
    longitude?: number;
    latitude?: number;
    pincode?: string;
    countryName?: string;
    countryid?: string;
}

export class Location implements ILocation {
    constructor(
        public id?: string,
        public address?: string,
        public state?: string,
        public city?: string,
        public locality?: string,
        public longitude?: number,
        public latitude?: number,
        public pincode?: string,
        public countryName?: string,
        public countryid?: string
    ) {}
}
