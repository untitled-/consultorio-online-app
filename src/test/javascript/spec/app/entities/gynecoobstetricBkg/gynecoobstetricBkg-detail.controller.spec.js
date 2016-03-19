'use strict';

describe('Controller Tests', function() {

    describe('GynecoobstetricBkg Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockGynecoobstetricBkg, MockContraceptives, MockPatient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockGynecoobstetricBkg = jasmine.createSpy('MockGynecoobstetricBkg');
            MockContraceptives = jasmine.createSpy('MockContraceptives');
            MockPatient = jasmine.createSpy('MockPatient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'GynecoobstetricBkg': MockGynecoobstetricBkg,
                'Contraceptives': MockContraceptives,
                'Patient': MockPatient
            };
            createController = function() {
                $injector.get('$controller')("GynecoobstetricBkgDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:gynecoobstetricBkgUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
