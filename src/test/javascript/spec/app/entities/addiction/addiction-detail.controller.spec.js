'use strict';

describe('Controller Tests', function() {

    describe('Addiction Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAddiction, MockPathologicBkg;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAddiction = jasmine.createSpy('MockAddiction');
            MockPathologicBkg = jasmine.createSpy('MockPathologicBkg');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Addiction': MockAddiction,
                'PathologicBkg': MockPathologicBkg
            };
            createController = function() {
                $injector.get('$controller')("AddictionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:addictionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
