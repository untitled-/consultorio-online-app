'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('GynecoobstetricBkgSearch', function ($resource) {
        return $resource('api/_search/gynecoobstetricBkgs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
