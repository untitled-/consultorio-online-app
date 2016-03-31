'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('AddictionSearch', function ($resource) {
        return $resource('api/_search/addictions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
